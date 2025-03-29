package com.IT22354938.repository;

import com.IT22354938.models.Income;
import com.IT22354938.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IncomeRepository extends MongoRepository<Income, String> {
    Optional<Income> findByName(String name);
    Optional<Income> findById(String id);
    Optional<Income> findByNic(String nic);


}
