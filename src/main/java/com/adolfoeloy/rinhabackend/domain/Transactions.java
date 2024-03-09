package com.adolfoeloy.rinhabackend.domain;

import com.adolfoeloy.rinhabackend.domain.entity.Transaction;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Transactions extends ListCrudRepository<Transaction, Integer> {

    List<Transaction> findTop10ByCustomerIdOrderByCreatedAtDesc(int customerId);

}
