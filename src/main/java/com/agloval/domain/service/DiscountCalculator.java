package com.agloval.domain.service;

import com.agloval.domain.entity.Product;
import com.agloval.domain.entity.QuotationLine;
import com.agloval.domain.enums.ProductCategory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DiscountCalculator {

    public BigDecimal calculateLineDiscount(Product product, int totalBoardCount, boolean isRegularCustomer) {
        BigDecimal discount = BigDecimal.ZERO;

        if (product.getCategory() == ProductCategory.TABLERO) {
            if (totalBoardCount >= 48) {
                discount = discount.add(new BigDecimal("6"));
            } else if (totalBoardCount >= 24) {
                discount = discount.add(new BigDecimal("3"));
            }

            if (product.getThicknessMm() != null && product.getThicknessMm() == 16) {
                discount = discount.add(new BigDecimal("3"));
            }
        }

        if (isRegularCustomer) {
            discount = discount.add(new BigDecimal("2"));
        }

        return discount;
    }

    public String buildDiscountBreakdown(Product product, int totalBoardCount, boolean isRegularCustomer) {
        List<String> parts = new ArrayList<>();

        if (product.getCategory() == ProductCategory.TABLERO) {
            if (totalBoardCount >= 48) {
                parts.add("Volume 6% (48+ boards)");
            } else if (totalBoardCount >= 24) {
                parts.add("Volume 3% (24+ boards)");
            }

            if (product.getThicknessMm() != null && product.getThicknessMm() == 16) {
                parts.add("16mm bonus 3%");
            }
        }

        if (isRegularCustomer) {
            parts.add("Regular customer 2%");
        }

        if (parts.isEmpty()) {
            return null;
        }

        BigDecimal total = calculateLineDiscount(product, totalBoardCount, isRegularCustomer);
        return String.join(" + ", parts) + " = " + total + "%";
    }

    public int countTotalBoards(List<QuotationLine> lines) {
        BigDecimal total = lines.stream()
                .filter(line -> line.getProduct().getCategory() == ProductCategory.TABLERO)
                .map(QuotationLine::getQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return total.intValue();
    }
}
