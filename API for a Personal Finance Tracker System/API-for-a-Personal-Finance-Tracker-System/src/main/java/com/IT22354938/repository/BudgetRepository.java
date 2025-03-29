package com.IT22354938.repository;

import com.IT22354938.models.Budget;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends MongoRepository<Budget,String> {
    Optional<Budget> findById(String id);
    Optional<Budget> findByNic(String nic);
    List<Budget> findByNicAndMonth(String nic, int month);
    List<Budget> findByNicAndCategory(String nic, String category);
    List<Budget> findByNicAndMonthAndCategory(String nic, String month, String category);
    List<Budget> findByNicAndMonthAndBudgetType(String nic, int month, String budgetType);
}
