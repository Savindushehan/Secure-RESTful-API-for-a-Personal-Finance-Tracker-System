package com.IT22354938.controller;

import com.IT22354938.dto.IncomeRequest;
import com.IT22354938.models.Income;
import com.IT22354938.service.IncomeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any; // Use Mockito's ArgumentMatchers.any()
import static org.mockito.ArgumentMatchers.eq; // Use Mockito's ArgumentMatchers.eq()
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class IncomeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IncomeService incomeService;

    @InjectMocks
    private IncomeController incomeController;

    @Test
    public void testCreateIncome() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(incomeController).build();

        // Update the IncomeRequest with all required parameters
        IncomeRequest request = new IncomeRequest(
                "1", "123456789V", "John Doe", 5000.00, "Salary",
                new Date(), "Monthly salary", "LKR", new Date(), new Date()
        );

        // Create the expected Income object
        Income income = new Income("1", "123456789V", "John Doe", 5000.00, "Salary",
                new Date(), "Monthly salary", "LKR", new Date(), new Date());

        // Mocking the service call
        when(incomeService.createIncome(any(IncomeRequest.class))).thenReturn(income);

        // Perform the request and validate the response using Hamcrest Matchers
        mockMvc.perform(post("/api/incomes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nic\": \"123456789V\", \"amount\": 5000.00, \"name\": \"John Doe\", \"source\": \"Salary\", \"description\": \"Monthly salary\", \"currency\": \"LKR\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nic", is("123456789V"))) // Hamcrest Matcher
                .andExpect(jsonPath("$.amount", is(5000.00))) // Hamcrest Matcher
                .andExpect(jsonPath("$.name", is("John Doe"))) // Hamcrest Matcher
                .andExpect(jsonPath("$.source", is("Salary"))) // Hamcrest Matcher
                .andExpect(jsonPath("$.description", is("Monthly salary"))) // Hamcrest Matcher
                .andExpect(jsonPath("$.currency", is("LKR"))); // Hamcrest Matcher

        verify(incomeService, times(1)).createIncome(any(IncomeRequest.class));
    }

    @Test
    public void testGetIncomeByNic() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(incomeController).build();

        // Create a mock income
        Income income = new Income("1", "123456789V", "John Doe", 5000.00, "Salary",
                new Date(), "Monthly salary", "LKR", new Date(), new Date());

        // Mocking the service call
        when(incomeService.getIncomeByNic("123456789V"))
                .thenReturn(new org.springframework.http.ResponseEntity<>(income, org.springframework.http.HttpStatus.OK));

        // Perform the request and validate the response using Hamcrest Matchers
        mockMvc.perform(get("/api/incomes/123456789V"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nic", is("123456789V"))) // Hamcrest Matcher
                .andExpect(jsonPath("$.amount", is(5000.00))) // Hamcrest Matcher
                .andExpect(jsonPath("$.name", is("John Doe"))) // Hamcrest Matcher
                .andExpect(jsonPath("$.source", is("Salary"))); // Hamcrest Matcher

        verify(incomeService, times(1)).getIncomeByNic("123456789V");
    }

    @Test
    public void testUpdateIncomeByName() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(incomeController).build();

        // Update the IncomeRequest with all required parameters
        IncomeRequest request = new IncomeRequest(
                "1", "123456789V", "John Doe", 6000.00, "Salary",
                new Date(), "Updated salary", "LKR", new Date(), new Date()
        );

        // Create the updated income object
        Income updatedIncome = new Income("1", "123456789V", "John Doe", 6000.00, "Salary",
                new Date(), "Updated salary", "LKR", new Date(), new Date());

        // Mocking the service call
        when(incomeService.updateIncomeByName(eq("1"), any(IncomeRequest.class)))
                .thenReturn(new org.springframework.http.ResponseEntity<>(updatedIncome, org.springframework.http.HttpStatus.OK));

        // Perform the request and validate the response using Hamcrest Matchers
        mockMvc.perform(put("/api/incomes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nic\": \"123456789V\", \"amount\": 6000.00, \"name\": \"John Doe\", \"source\": \"Salary\", \"description\": \"Updated salary\", \"currency\": \"LKR\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount", is(6000.00))) // Hamcrest Matcher
                .andExpect(jsonPath("$.description", is("Updated salary"))); // Hamcrest Matcher

        verify(incomeService, times(1)).updateIncomeByName(eq("1"), any(IncomeRequest.class));
    }

    @Test
    public void testDeleteIncomeByName() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(incomeController).build();

        // Mocking the delete service call
        doNothing().when(incomeService).deleteIncomeByName("65df13b24f6e4d001c3e99a7");

        // Perform the request and validate the response
        mockMvc.perform(delete("/api/incomes/65df13b24f6e4d001c3e99a7"))
                .andExpect(status().isOk());

        verify(incomeService, times(1)).deleteIncomeByName("65df13b24f6e4d001c3e99a7");
    }
}