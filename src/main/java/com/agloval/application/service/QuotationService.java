package com.agloval.application.service;

import com.agloval.application.dto.QuotationLineRequest;
import com.agloval.application.dto.QuotationLineResponse;
import com.agloval.application.dto.QuotationRequest;
import com.agloval.application.dto.QuotationResponse;
import com.agloval.application.port.in.QuotationUseCase;
import com.agloval.application.port.out.ProductRepositoryPort;
import com.agloval.application.port.out.QuotationRepositoryPort;
import com.agloval.application.port.out.UserRepositoryPort;
import com.agloval.domain.entity.Product;
import com.agloval.domain.entity.Quotation;
import com.agloval.domain.entity.QuotationLine;
import com.agloval.domain.entity.User;
import com.agloval.domain.enums.QuotationStatus;
import com.agloval.domain.exception.ProductNotFoundException;
import com.agloval.domain.exception.QuotationNotFoundException;
import com.agloval.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class QuotationService implements QuotationUseCase {

    private final QuotationRepositoryPort quotationRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final ProductRepositoryPort productRepositoryPort;

    @Override
    public QuotationResponse createQuotation(QuotationRequest request) {
        User user = userRepositoryPort.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));

        Quotation quotation = Quotation.builder()
                .quotationNumber(generateQuotationNumber())
                .user(user)
                .validityDays(request.getValidityDays() != null ? request.getValidityDays() : 30)
                .notes(request.getNotes())
                .build();

        List<QuotationLine> lines = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (QuotationLineRequest lineReq : request.getLines()) {
            Product product = productRepositoryPort.findById(lineReq.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(lineReq.getProductId()));

            BigDecimal unitPrice = resolveUnitPrice(product);
            BigDecimal discount = lineReq.getDiscountPercent() != null
                    ? lineReq.getDiscountPercent()
                    : BigDecimal.ZERO;
            BigDecimal discountFactor = BigDecimal.ONE.subtract(
                    discount.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
            BigDecimal lineTotal = lineReq.getQuantity()
                    .multiply(unitPrice)
                    .multiply(discountFactor)
                    .setScale(2, RoundingMode.HALF_UP);

            lines.add(QuotationLine.builder()
                    .quotation(quotation)
                    .product(product)
                    .quantity(lineReq.getQuantity())
                    .unitPrice(unitPrice)
                    .discountPercent(discount)
                    .lineTotal(lineTotal)
                    .description(lineReq.getDescription())
                    .build());

            subtotal = subtotal.add(lineTotal);
        }

        quotation.setLines(lines);
        quotation.setSubtotal(subtotal);
        quotation.setTotal(subtotal);

        return toResponse(quotationRepositoryPort.save(quotation));
    }

    @Override
    @Transactional(readOnly = true)
    public QuotationResponse getQuotationById(Long id) {
        return toResponse(quotationRepositoryPort.findById(id)
                .orElseThrow(() -> new QuotationNotFoundException(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuotationResponse> getAllQuotations() {
        return quotationRepositoryPort.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public QuotationResponse updateStatus(Long id, QuotationStatus status) {
        Quotation quotation = quotationRepositoryPort.findById(id)
                .orElseThrow(() -> new QuotationNotFoundException(id));
        quotation.setStatus(status);
        return toResponse(quotationRepositoryPort.save(quotation));
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuotationResponse> getQuotationsByUserId(Long userId) {
        userRepositoryPort.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return quotationRepositoryPort.findByUserId(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    private BigDecimal resolveUnitPrice(Product product) {
        if (product.getPricePerUnit() != null) return product.getPricePerUnit();
        if (product.getPricePerM2() != null) return product.getPricePerM2();
        if (product.getPricePerRateUnit() != null) return product.getPricePerRateUnit();
        return BigDecimal.ZERO;
    }

    private String generateQuotationNumber() {
        String suffix = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "Q-" + LocalDate.now().getYear() + "-" + suffix;
    }

    private QuotationResponse toResponse(Quotation quotation) {
        List<QuotationLineResponse> lineResponses = quotation.getLines().stream()
                .map(this::toLineResponse)
                .toList();

        return QuotationResponse.builder()
                .id(quotation.getId())
                .quotationNumber(quotation.getQuotationNumber())
                .userId(quotation.getUser().getId())
                .userName(quotation.getUser().getName())
                .userEmail(quotation.getUser().getEmail())
                .status(quotation.getStatus())
                .createdAt(quotation.getCreatedAt())
                .expiryDate(quotation.getExpiryDate())
                .validityDays(quotation.getValidityDays())
                .subtotal(quotation.getSubtotal())
                .discountAmount(quotation.getDiscountAmount())
                .total(quotation.getTotal())
                .notes(quotation.getNotes())
                .lines(lineResponses)
                .build();
    }

    private QuotationLineResponse toLineResponse(QuotationLine line) {
        return QuotationLineResponse.builder()
                .id(line.getId())
                .productId(line.getProduct().getId())
                .productName(line.getProduct().getName())
                .quantity(line.getQuantity())
                .unitPrice(line.getUnitPrice())
                .discountPercent(line.getDiscountPercent())
                .lineTotal(line.getLineTotal())
                .description(line.getDescription())
                .build();
    }
}
