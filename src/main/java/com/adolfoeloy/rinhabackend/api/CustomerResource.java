package com.adolfoeloy.rinhabackend.api;

import com.adolfoeloy.rinhabackend.domain.customer.CustomerService;
import com.adolfoeloy.rinhabackend.domain.customer.Customers;
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

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("clientes")
public class CustomerResource {

    private final Customers customers;
    private final CustomerService customerService;
    private final CustomerConverter customerConverter;

    CustomerResource(
            Customers customers,
            CustomerService customerService,
            CustomerConverter customerConverter) {
        this.customers = customers;
        this.customerService = customerService;
        this.customerConverter = customerConverter;
    }

    @GetMapping("/{id}/extrato")
    ResponseEntity<Statement> statement(@PathVariable("id") int customerId) {
        return customers.findById(customerId)
                .map(customerConverter::convert)
                .map(ResponseEntity::ok)
                .orElseGet(() -> notFound().build());
    }

    @PostMapping("/{id}/transacoes")
    ResponseEntity<SimpleBalance> transaction(
            @PathVariable("id") int id,
            @RequestBody Transaction transaction
    ) {
        return customers.findById(id)
                .map(customer -> customerService.addTransactionEntryFor(
                        customer, transaction.value(),transaction.type(), transaction.description()))
                .map(customer -> new SimpleBalance(customer.getLimit(), customer.getBalance()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> notFound().build());
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
