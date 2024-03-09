package com.adolfoeloy.rinhabackend.customer.api;

import com.adolfoeloy.rinhabackend.customer.model.Customer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomerConverter implements Converter<Customer, CustomerResource.Statement> {
    @Override
    public CustomerResource.Statement convert(Customer customer) {
        var transactions = customer.getTransactions().stream()
                .map(t -> new CustomerResource.Transaction(
                        t.getAmount(),
                        t.getType(),
                        t.getDescription(),
                        t.getCreatedAt()))
                .toList();

        var customerTransactions = new CustomerResource.Transactions(transactions);

        return new CustomerResource.Statement(
                new CustomerResource.Balance(
                        customerTransactions.getTotal(),
                        LocalDateTime.now(),
                        customer.getLimit()),
                customerTransactions.all()
        );
    }
}
