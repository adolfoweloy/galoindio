package com.adolfoeloy.rinhabackend.domain.customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Customers extends CrudRepository<Customer, Integer> {
}
