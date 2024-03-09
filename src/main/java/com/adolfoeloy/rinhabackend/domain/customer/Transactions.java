package com.adolfoeloy.rinhabackend.domain.customer;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Transactions extends ListCrudRepository<Transaction, Integer> {
}
