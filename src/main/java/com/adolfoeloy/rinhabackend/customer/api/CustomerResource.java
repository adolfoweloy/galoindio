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
    private final com.adolfoeloy.rinhabackend.customer.model.Transactions transactions;
    private final CustomerConverter customerConverter;

    CustomerResource(
            Customers customers,
            com.adolfoeloy.rinhabackend.customer.model.Transactions transactions,
            CustomerConverter customerConverter) {
        this.customers = customers;
        this.transactions = transactions;
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
            @PathVariable("id") int id,
            @RequestBody Transaction transaction
    ) {
        return customers.findById(id)
                .map(customer -> new com.adolfoeloy.rinhabackend.customer.model.Transaction(
                            customer,
                            transaction.value(),
                            transaction.type(),
                            transaction.description()))
                .map(transactions::save)
                .map(t -> {
                    var customer = t.getCustomer();
                    var amount = (t.getType() == 'c') ? t.getAmount() : t.getAmount() * -1;
                    customer.setBalance(customer.getBalance() + amount);
                    return customers.save(customer);
                })
                .map(customer -> new SimpleBalance(customer.getLimit(), customer.getBalance()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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
