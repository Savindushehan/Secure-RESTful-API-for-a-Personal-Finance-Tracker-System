package com.IT22354938.controller;

import com.IT22354938.dto.IncomeRequest;
import com.IT22354938.dto.UserRequest;
import com.IT22354938.models.Income;
import com.IT22354938.models.User;
import com.IT22354938.service.IncomeService;
import com.IT22354938.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000") // Allow requests from React app
@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping
    public ResponseEntity<Income> createIncome(@RequestBody IncomeRequest incomeRequest) {
        Income income = incomeService.createIncome(incomeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(income);
    }

    @GetMapping("/{nic}")
    public ResponseEntity<Income> getIncomeByNic(@PathVariable String nic) { // ✅ Fixed method name
        return incomeService.getIncomeByNic(nic);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Income> updateIncomeByName(@PathVariable String id, @RequestBody IncomeRequest incomeRequest) {
        return incomeService.updateIncomeByName(id, incomeRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncomeByName(@PathVariable String id) {
        return incomeService.deleteIncomeByName(id);
    }

}
