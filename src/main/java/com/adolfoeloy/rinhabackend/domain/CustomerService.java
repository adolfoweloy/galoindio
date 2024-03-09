package com.adolfoeloy.rinhabackend.domain;

import com.adolfoeloy.rinhabackend.domain.entity.Customer;
import com.adolfoeloy.rinhabackend.domain.entity.Transaction;
import com.adolfoeloy.rinhabackend.domain.values.BalanceVO;
import com.adolfoeloy.rinhabackend.domain.values.StatementVO;
import com.adolfoeloy.rinhabackend.domain.values.TransactionVO;
import com.adolfoeloy.rinhabackend.domain.values.TransactionsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CustomerService {
    private final Customers customers;
    private final Transactions transactions;

    public CustomerService(Customers customers, Transactions transactions) {
        this.customers = customers;
        this.transactions = transactions;
    }

    public Optional<StatementVO> findStatementFor(int customerId) {
        return customers
            .findById(customerId)
            .map(customer -> {
                var transactionsFound = transactions.findTop10ByCustomerIdOrderByCreatedAtDesc(customer.getId());

                var transactions = new TransactionsVO(
                    transactionsFound.stream()
                        .map(CustomerService::convertTransaction)
                        .toList());

                return new StatementVO(
                        createBalance(customer.getLimit(), transactions.getTotal()),
                        transactions.all());
            });
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

    private static BalanceVO createBalance(int customerLimit, int totalBalance) {
        return new BalanceVO(totalBalance, LocalDateTime.now(), customerLimit);
    }

    private static TransactionVO convertTransaction(Transaction t) {
        return new TransactionVO(t.getAmount(), t.getType(), t.getDescription(), t.getCreatedAt());
    }

}
