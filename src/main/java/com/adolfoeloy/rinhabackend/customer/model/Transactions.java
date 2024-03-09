package com.adolfoeloy.rinhabackend.customer.model;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Transactions extends ListCrudRepository<Transaction, Integer> {
}
