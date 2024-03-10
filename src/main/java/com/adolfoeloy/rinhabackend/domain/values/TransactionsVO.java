package com.adolfoeloy.rinhabackend.domain.values;

import java.util.List;

public record TransactionsVO(List<TransactionVO> transactions) {
    public int getTotal() {
        return transactions.stream()
                .mapToInt(TransactionVO::getValue)
                .sum();
    }

    public List<TransactionVO> all() {
        return transactions;
    }
}
