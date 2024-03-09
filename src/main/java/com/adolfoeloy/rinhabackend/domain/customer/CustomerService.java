package com.adolfoeloy.rinhabackend.domain.customer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {
    private final Customers customers;
    private final Transactions transactions;

    public CustomerService(Customers customers, Transactions transactions) {
        this.customers = customers;
        this.transactions = transactions;
    }

    @Transactional
    public Customer addTransactionEntryFor(Customer customer, int amount, char type, String description) {
        var transaction = customer.createTransaction(amount, type, description);
        transactions.save(transaction);
        customer.updateBalance(type, amount);
        return customers.save(customer);
    }
}
