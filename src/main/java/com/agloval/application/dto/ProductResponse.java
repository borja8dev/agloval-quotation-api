package com.agloval.application.dto;

import com.agloval.domain.enums.ProductCategory;
import com.agloval.domain.enums.RateType;
import com.agloval.domain.enums.SaleUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String name;
    private ProductCategory category;
    private SaleUnit saleUnit;
    private BigDecimal pricePerM2;
    private BigDecimal pricePerUnit;
    private BigDecimal pricePerRateUnit;
    private RateType rateType;
    private Integer widthCm;
    private Integer lengthCm;
    private Integer thicknessMm;
    private String color;
}
