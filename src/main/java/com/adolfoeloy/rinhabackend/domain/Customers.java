package com.adolfoeloy.rinhabackend.domain;

import com.adolfoeloy.rinhabackend.domain.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Customers extends CrudRepository<Customer, Integer> {

}
