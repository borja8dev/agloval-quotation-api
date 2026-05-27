package com.agloval.application.dto;

import com.agloval.domain.enums.QuotationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuotationResponse {

    private Long id;
    private String quotationNumber;
    private Long userId;
    private String userName;
    private String userEmail;
    private QuotationStatus status;
    private LocalDate createdAt;
    private LocalDate expiryDate;
    private Integer validityDays;
    private BigDecimal subtotal;
    private BigDecimal discountAmount;
    private BigDecimal total;
    private String notes;
    private List<QuotationLineResponse> lines;
}
