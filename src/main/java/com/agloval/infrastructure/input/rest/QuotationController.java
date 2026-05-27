package com.agloval.infrastructure.input.rest;

import com.agloval.application.dto.QuotationRequest;
import com.agloval.application.dto.QuotationResponse;
import com.agloval.application.port.in.QuotationUseCase;
import com.agloval.domain.enums.QuotationStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quotations")
@RequiredArgsConstructor
@Tag(name = "Quotations", description = "Quotation management")
public class QuotationController {

    private final QuotationUseCase quotationUseCase;

    @PostMapping
    @Operation(summary = "Create a new quotation")
    public ResponseEntity<QuotationResponse> createQuotation(@Valid @RequestBody QuotationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(quotationUseCase.createQuotation(request));
    }

    @GetMapping
    @Operation(summary = "Get all quotations")
    public ResponseEntity<List<QuotationResponse>> getAllQuotations() {
        return ResponseEntity.ok(quotationUseCase.getAllQuotations());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get quotation by ID")
    public ResponseEntity<QuotationResponse> getQuotationById(@PathVariable Long id) {
        return ResponseEntity.ok(quotationUseCase.getQuotationById(id));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update quotation status")
    public ResponseEntity<QuotationResponse> updateStatus(@PathVariable Long id,
                                                           @RequestParam QuotationStatus status) {
        return ResponseEntity.ok(quotationUseCase.updateStatus(id, status));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get quotations by user ID")
    public ResponseEntity<List<QuotationResponse>> getQuotationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(quotationUseCase.getQuotationsByUserId(userId));
    }
}
