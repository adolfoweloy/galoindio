package com.adolfoeloy.rinhabackend.domain.values;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * Value Object for transactions
 */
public record TransactionVO(
    @JsonProperty("valor")
    int value,

    @JsonProperty("tipo")
    char type,

    @JsonProperty("descricao")
    String description,

    @JsonProperty("realizada_em")
    LocalDateTime createdAt
) {
    int getValue() {
        if (type == 'c') {
            return value;
        }
        return -value;
    }
}
