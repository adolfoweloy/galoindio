package com.adolfoeloy.rinhabackend.customer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "transacoes")
@SequenceGenerator(name = "transaction_gen", sequenceName = "transacoes_sq", allocationSize = 1)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_gen")
    private int id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Customer customer;

    @Column(name = "valor")
    private int amount;

    @Column(name = "tipo")
    private char type;

    @Column(name = "descricao")
    private String description;

    @Column(name = "realizada_em")
    private LocalDateTime createdAt;

    @Deprecated
    Transaction() {
    }

    public Transaction(Customer customer, int amount, char type, String description) {
        this.customer = customer;
        this.amount = amount;
        this.type = type;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getAmount() {
        return amount;
    }

    public char getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
