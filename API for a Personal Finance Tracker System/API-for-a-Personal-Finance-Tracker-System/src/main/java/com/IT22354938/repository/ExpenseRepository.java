package com.IT22354938.repository;

import com.IT22354938.models.expense;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends MongoRepository<expense,String> {

    Optional<expense> findByName(String name);
    Optional<expense> findById(String id);
    Optional<expense> findByNic(String nic);

    List<expense> findByRecurringTrue();
    List<expense> findByCategory(String category);

}
