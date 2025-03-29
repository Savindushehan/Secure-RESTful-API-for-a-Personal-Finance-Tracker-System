package com.IT22354938.controller;


import com.IT22354938.dto.BudgetRequest;
import com.IT22354938.models.Budget;
import com.IT22354938.service.BudgetService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(title = "Budget API", version = "1.0", description = "API for managing budgets")
)
@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @Operation(summary = "Create a new budget", description = "Creates a new budget based on the provided request")
    @PostMapping
    public ResponseEntity<Budget> createBudget(@RequestBody BudgetRequest budgetRequest) {
        Budget budget = budgetService.createBudget(budgetRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(budget);
    }

    @Operation(summary = "Get budget by NIC", description = "Retrieves a budget by NIC")
    @GetMapping("/{nic}")
    public ResponseEntity<Budget> getBudgetByNic(@PathVariable String nic) {
        return budgetService.getBudgetByNIC(nic);
    }

    @Operation(summary = "Update budget by ID", description = "Updates an existing budget using its ID")
    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudgetsByName(@PathVariable String id, @RequestBody BudgetRequest budgetRequest) {
        return budgetService.updateBudgetByName(id, budgetRequest);
    }

    @Operation(summary = "Delete budget by ID", description = "Deletes a budget by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudgetByName(@PathVariable String id) {
        return budgetService.deletebudgetByName(id);
    }

    @Operation(summary = "Get budgets by NIC and month", description = "Retrieves budgets filtered by NIC and month")
    @GetMapping("/{nic}/{month}")
    public ResponseEntity<List<Budget>> getBudgetByNICandMonth(
            @PathVariable String nic,
            @PathVariable int month) {

        return budgetService.getBudgetByNICandMonth(nic, month);
    }

    @Operation(summary = "Get budgets by NIC and category", description = "Retrieves budgets filtered by NIC and category")
    @GetMapping("/{nic}/{category}")
    public ResponseEntity<List<Budget>> getBudgetByNICandCategory(
            @PathVariable String nic,
            @PathVariable String category) {

        return budgetService.getBudgetByNICandCategory(nic, category);
    }

}
