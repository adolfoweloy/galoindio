package com.adolfoeloy.rinhabackend.customer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

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

    @OneToMany(mappedBy = "customer")
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

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
