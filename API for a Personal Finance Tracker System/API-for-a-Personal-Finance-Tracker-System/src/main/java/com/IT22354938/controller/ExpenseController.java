package com.IT22354938.controller;

import com.IT22354938.dto.ExpenseRequest;
import com.IT22354938.models.expense;
import com.IT22354938.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

//    @PostMapping
//    public ResponseEntity<expense> createIncome(@RequestBody ExpenseRequest expenseRequest) {
//        expense expense = expenseService.createExpense(expenseRequest);
//        return ResponseEntity.status(HttpStatus.CREATED).body(expense);
//    }
    @PostMapping
    public ResponseEntity<?> createIncome(@RequestBody ExpenseRequest expenseRequest) {
        return expenseService.createExpense(expenseRequest);
    }

    @GetMapping("/{nic}")
    public ResponseEntity<expense> getExpenseByNic(@PathVariable String nic) {
        return expenseService.getExpenseByNIC(nic);
    }


    @PutMapping("/{id}")
    public ResponseEntity<expense> updateExpenseByName(@PathVariable String id, @RequestBody ExpenseRequest expenseRequest) {
        return expenseService.updateExpenseByName(id, expenseRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpenseByName(@PathVariable String id) {
        return expenseService.deleteExpenseByName(id);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<expense>> getExpensesByCategory(@PathVariable String category) {
        return expenseService.getExpensesByCategory(category);
    }

    @GetMapping
    public ResponseEntity<List<expense>> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

}
