package com.IT22354938.service;

import com.IT22354938.dto.BudgetRequest;
import com.IT22354938.models.Budget;

import com.IT22354938.repository.BudgetRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public Budget createBudget(BudgetRequest budgetRequest){
        Budget budget = new Budget(
                budgetRequest.id(),
                budgetRequest.nic(),
                budgetRequest.budgetType(),
                budgetRequest.month(),
                budgetRequest.category(),
                budgetRequest.amount(),
                budgetRequest.createdAt()

        );
        return budgetRepository.save(budget);
    }

    public ResponseEntity<Budget> getBudgetByNIC(String nic){
        System.out.println("Fetching budget for NIC: " + nic);
        Optional<Budget> budget = budgetRepository.findByNic(nic);
        System.out.println("Result: " + budget);
        return budget.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    public ResponseEntity<List<Budget>> getBudgetByNICandMonth(String nic, int month) {
        System.out.println("Fetching budget for NIC: " + nic + " and Month: " + month);

        List<Budget> budgets = budgetRepository.findByNicAndMonth(nic, month);

        if (budgets.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(budgets);
        }
    }

    public ResponseEntity<List<Budget>> getBudgetByNICandCategory(String nic, String category) {
        System.out.println("Fetching budget for NIC: " + nic + " and Category: " + category);

        List<Budget> budgets = budgetRepository.findByNicAndCategory(nic, category);

        if (budgets.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(budgets);
        }
    }


    public ResponseEntity<Budget> updateBudgetByName(String id, BudgetRequest budgetRequest) {
        Optional<Budget> optionalbudget = budgetRepository.findById(id);

        if (optionalbudget.isPresent()) {
            Budget budget = optionalbudget.get();
            budget.setNic(budgetRequest.nic());
            budget.setBudgetType(budgetRequest.budgetType());
            budget.setMonth(budgetRequest.month());
            budget.setCategory(budgetRequest.category());
            budget.setAmount(budgetRequest.amount());
            budget.setCreatedAt(budgetRequest.createdAt());



            return ResponseEntity.ok(budgetRepository.save(budget));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Void> deletebudgetByName(String id) {
        Optional<Budget> optionalExpense = budgetRepository.findById(id);

        if (optionalExpense.isPresent()) {
            budgetRepository.delete(optionalExpense.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Budget> updateBudgetAmount(String nic, int month, String budgetType, double remainingBudget) {
        List<Budget> budgets = budgetRepository.findByNicAndMonthAndCategory(nic,String.valueOf(month), budgetType);

        if (budgets.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Budget budget = budgets.get(0); // Get the first matching budget
        budget.setAmount(remainingBudget); // Update the budget amount

        Budget updatedBudget = budgetRepository.save(budget); // Save changes

        return ResponseEntity.ok(updatedBudget);
    }





}
