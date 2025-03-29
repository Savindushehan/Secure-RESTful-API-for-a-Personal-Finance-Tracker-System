package com.IT22354938.controller;

import com.IT22354938.dto.BudgetRequest;
import com.IT22354938.models.Budget;
import com.IT22354938.service.BudgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BudgetControllerTest {

    @Mock
    private BudgetService budgetService;

    @InjectMocks
    private BudgetController budgetController;

    private BudgetRequest budgetRequest;
    private Budget budget;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize budgetRequest with sample data
        budgetRequest = new BudgetRequest(
                "1", // id
                "123456789V", // NIC
                "Annual", // Budget Type
                "March", // Month
                "Travel", // Category
                5000.0, // Amount
                new Date() // Created At (current date)
        );

        // Initialize budget (you may modify according to your actual Budget class)
        budget = new Budget();
        budget.setId("1");
        budget.setNic("123456789V");
        budget.setBudgetType("Annual");
        budget.setMonth("March");
        budget.setCategory("Travel");
        budget.setAmount(5000.0);
        budget.setCreatedAt(new Date());
    }

    @Test
    void testCreateBudget() {
        when(budgetService.createBudget(budgetRequest)).thenReturn(budget);

        ResponseEntity<Budget> response = budgetController.createBudget(budgetRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(budget, response.getBody());
        verify(budgetService, times(1)).createBudget(budgetRequest);
    }

    @Test
    void testGetBudgetByNic() {
        String nic = "123456789V";
        when(budgetService.getBudgetByNIC(nic)).thenReturn(new ResponseEntity<>(budget, HttpStatus.OK));

        ResponseEntity<Budget> response = budgetController.getBudgetByNic(nic);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(budget, response.getBody());
        verify(budgetService, times(1)).getBudgetByNIC(nic);
    }

    @Test
    void testUpdateBudgetById() {
        String id = "1";
        when(budgetService.updateBudgetByName(id, budgetRequest)).thenReturn(new ResponseEntity<>(budget, HttpStatus.OK));

        ResponseEntity<Budget> response = budgetController.updateBudgetsByName(id, budgetRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(budget, response.getBody());
        verify(budgetService, times(1)).updateBudgetByName(id, budgetRequest);
    }

    @Test
    void testDeleteBudgetById() {
        String id = "1";
        when(budgetService.deletebudgetByName(id)).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        ResponseEntity<Void> response = budgetController.deleteBudgetByName(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(budgetService, times(1)).deletebudgetByName(id);
    }

    @Test
    void testGetBudgetByNicAndMonth() {
        String nic = "123456789V";
        int month = 3; // March
        when(budgetService.getBudgetByNICandMonth(nic, month)).thenReturn(new ResponseEntity<>(List.of(budget), HttpStatus.OK));

        ResponseEntity<List<Budget>> response = budgetController.getBudgetByNICandMonth(nic, month);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(budget, response.getBody().get(0));
        verify(budgetService, times(1)).getBudgetByNICandMonth(nic, month);
    }

    @Test
    void testGetBudgetByNicAndCategory() {
        String nic = "123456789V";
        String category = "Travel";
        when(budgetService.getBudgetByNICandCategory(nic, category)).thenReturn(new ResponseEntity<>(List.of(budget), HttpStatus.OK));

        ResponseEntity<List<Budget>> response = budgetController.getBudgetByNICandCategory(nic, category);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(budget, response.getBody().get(0));
        verify(budgetService, times(1)).getBudgetByNICandCategory(nic, category);
    }
}
