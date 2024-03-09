package com.adolfoeloy.rinhabackend.customer.api;

import com.adolfoeloy.rinhabackend.customer.model.Customers;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("clientes")
public class CustomerResource {

    private final Customers customers;
    private final CustomerConverter customerConverter;

    public CustomerResource(Customers customers, CustomerConverter customerConverter) {
        this.customers = customers;
        this.customerConverter = customerConverter;
    }

    @GetMapping("/{id}/extrato")
    ResponseEntity<Statement> statement(@PathVariable("id") int customerId) {
        return customers.findById(customerId)
                .map(customerConverter::convert)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/transacoes")
    ResponseEntity<SimpleBalance> transaction(
            @PathVariable int id,
            @RequestBody Transaction transaction
    ) {

        return ResponseEntity.ok(new SimpleBalance(10000, -9098));
    }

    record SimpleBalance(
        int limit,
        int balance
    ) {}

    record Statement(
            @JsonProperty("saldo")
            Balance balance,
            @JsonProperty("ultimas_transacoes")
            List<Transaction> lastTransactions
    ) {}

    record Balance(
            int total,
            @JsonProperty("data_extrato")
            LocalDateTime date,
            @JsonProperty("limite")
            int limit
    ) {}

    record Transactions(List<Transaction> transactions) {
        int getTotal() {
            return transactions.stream()
                    .mapToInt(Transaction::value)
                    .sum();
        }

        List<Transaction> all() {
            return transactions;
        }
    }

    record Transaction(
            @JsonProperty("valor")
            int value,
            @JsonProperty("tipo")
            char type,
            @JsonProperty("descricao")
            String description,
            @JsonProperty("realizada_em")
            LocalDateTime createdAt
    ) { }
}
