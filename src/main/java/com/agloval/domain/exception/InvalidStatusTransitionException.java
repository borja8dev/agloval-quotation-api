package com.agloval.domain.exception;

import com.agloval.domain.enums.QuotationStatus;

public class InvalidStatusTransitionException extends RuntimeException {

    public InvalidStatusTransitionException(QuotationStatus from, QuotationStatus to) {
        super("Invalid status transition from " + from + " to " + to);
    }
}
