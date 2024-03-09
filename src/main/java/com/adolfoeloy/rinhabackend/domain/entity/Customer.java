package com.adolfoeloy.rinhabackend.domain.entity;

import com.adolfoeloy.rinhabackend.domain.exception.CustomerLimitExceededException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientes_generator")
    @SequenceGenerator(name = "clientes_generator", sequenceName = "clientes_sq", initialValue = 1, allocationSize = 1)
    private int id;

    @Column(length = 10)
    private String nome;

    @Column(name = "limite")
    private int limit;

    @Column(name = "saldo")
    private int balance;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    @Deprecated
    Customer() {}

    public Customer(
            String nome,
            int limit,
            int balance
    ) {
        this.nome = nome;
        this.limit = limit;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getLimit() {
        return limit;
    }

    public int getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void updateBalance(char type, int amount) {
        var operation = (type == 'c') ? amount : amount * -1;
        if (balance + operation + limit < 0) {
            throw new CustomerLimitExceededException();
        }
        this.balance += (type == 'c') ? amount : amount * -1;
    }

    public Transaction createTransaction(int amount, char type, String description) {
        return new Transaction(this, amount, type, description, LocalDateTime.now());
    }
}
