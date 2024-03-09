package com.adolfoeloy.rinhabackend.api;

import com.adolfoeloy.rinhabackend.api.validation.ValidTransactionType;
import com.adolfoeloy.rinhabackend.domain.CustomerService;
import com.adolfoeloy.rinhabackend.domain.values.StatementVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("clientes")
public class CustomerResource {

    private final CustomerService customerService;

    CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}/extrato")
    ResponseEntity<StatementVO> statement(@PathVariable("id") int customerId) {
        return customerService.findStatementFor(customerId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> notFound().build());
    }

    @PostMapping("/{id}/transacoes")
    ResponseEntity<SimpleBalance> transaction(
            @PathVariable("id") int id,
            @Valid @RequestBody TransactionRequest transaction
    ) {
        return customerService.addTransactionEntryFor(
                    id, transaction.value(),transaction.type(), transaction.description())
                .map(customer -> new SimpleBalance(customer.getLimit(), customer.getBalance()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> notFound().build());
    }

    record SimpleBalance(
        int limit,
        int balance
    ) {}


    record TransactionRequest(
            @Min(value = 0, message = "Valor deve ser positivo")
            @JsonProperty("valor")
            int value,

            @ValidTransactionType
            @JsonProperty("tipo")
            char type,

            @Length(min = 1, max = 10, message = "Descricao deve ter no entre 1 e 10 caracteres")
            @JsonProperty("descricao")
            String description
    ) { }
}
