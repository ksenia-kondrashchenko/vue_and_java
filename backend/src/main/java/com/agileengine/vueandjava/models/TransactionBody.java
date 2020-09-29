package com.agileengine.vueandjava.models;

import com.agileengine.vueandjava.constants.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionBody {

    @JsonProperty
    private TransactionType type;

    @JsonProperty
    private BigDecimal amount;
}
