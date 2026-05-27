package com.agloval.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuotationRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    private Integer validityDays;

    private String notes;

    @NotEmpty(message = "Quotation must have at least one line")
    @Valid
    private List<QuotationLineRequest> lines;
}
