package com.agileengine.vueandjava.models;

import com.agileengine.vueandjava.constants.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Transaction {

    @JsonProperty
    private String id;

    @JsonProperty
    private TransactionType type;

    @JsonProperty
    private BigDecimal amount;

    @JsonProperty
    private Date effectiveDate;
}
