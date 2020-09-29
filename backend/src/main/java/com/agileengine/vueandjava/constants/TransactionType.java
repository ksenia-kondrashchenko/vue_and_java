package com.agileengine.vueandjava.constants;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionType {
    DEBIT("debit"),
    CREDIT("credit");

    private String value;

    TransactionType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
