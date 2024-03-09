package com.adolfoeloy.rinhabackend.domain.values;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record StatementVO(
        @JsonProperty("saldo")
        BalanceVO balance,
        @JsonProperty("ultimas_transacoes")
        List<TransactionVO> lastTransactions
) {
}
