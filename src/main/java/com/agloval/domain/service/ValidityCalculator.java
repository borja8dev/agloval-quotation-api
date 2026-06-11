package com.agloval.domain.service;

import java.time.LocalDate;

public class ValidityCalculator {

    public int calculateValidityDays(boolean isRegularCustomer, LocalDate date) {
        int month = date.getMonthValue();
        boolean isSummerOrHoliday = (month >= 6 && month <= 8) || month == 12;

        if (isSummerOrHoliday) {
            return isRegularCustomer ? 60 : 30;
        } else {
            return isRegularCustomer ? 90 : 45;
        }
    }
}
