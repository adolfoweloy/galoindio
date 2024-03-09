package com.adolfoeloy.rinhabackend.domain.values;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record BalanceVO(
        int total,
        @JsonProperty("data_extrato")
        LocalDateTime date,
        @JsonProperty("limite")
        int limit
) {
}
