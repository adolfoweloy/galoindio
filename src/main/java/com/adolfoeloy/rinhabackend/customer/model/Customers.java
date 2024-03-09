package com.adolfoeloy.rinhabackend.customer.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Customers extends CrudRepository<Customer, Integer> {

}
