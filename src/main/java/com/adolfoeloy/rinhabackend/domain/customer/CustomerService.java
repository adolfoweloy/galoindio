package com.adolfoeloy.rinhabackend.domain.customer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerService {
    private final Customers customers;
    private final Transactions transactions;

    public CustomerService(Customers customers, Transactions transactions) {
        this.customers = customers;
        this.transactions = transactions;
    }

    @Transactional
    public Optional<Customer> addTransactionEntryFor(int customerId, int amount, char type, String description) {
        var optionalCustomer = customers.findById(customerId);
        if (optionalCustomer.isPresent()) {
            var customer = optionalCustomer.get();

            // good to go with the transaction
            var transaction = customer.createTransaction(amount, type, description);
            transactions.save(transaction);
            customer.updateBalance(type, amount);
            return Optional.of(customers.save(customer));
        }
        return Optional.empty();
    }
}
