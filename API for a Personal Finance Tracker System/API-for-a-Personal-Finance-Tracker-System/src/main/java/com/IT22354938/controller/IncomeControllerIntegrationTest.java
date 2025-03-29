package com.IT22354938.controller;

import com.IT22354938.dto.IncomeRequest;
import com.IT22354938.models.Income;
import com.IT22354938.service.IncomeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ExtendWith(MockitoExtension.class) // Enable Mockito in the test
public class IncomeControllerIntegrationTest {

    @Container
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer(
            DockerImageName.parse("mongo:latest") // Use DockerImageName
    );

    @Autowired
    private MockMvc mockMvc;

    @Mock // Use Mockito's @Mock instead of @MockBean
    private IncomeService incomeService;

    @InjectMocks // Inject the mocks into the controller
    private IncomeController incomeController;

    @Autowired
    private ObjectMapper objectMapper;

    private IncomeRequest incomeRequest;
    private Income income;

    @BeforeEach
    void setUp() {
        Date now = new Date();
        incomeRequest = new IncomeRequest(
                "1", // id
                "123456789", // nic
                "John Doe", // name
                5000.0, // amount
                "Salary", // source
                now, // incomeDate
                "Monthly salary", // description
                "USD", // currency
                now, // createdAt
                now // updatedAt
        );

        income = new Income();
        income.setId("1");
        income.setNic("123456789");
        income.setName("John Doe");
        income.setAmount(5000.0);
        income.setSource("Salary");
        income.setIncomeDate(now);
        income.setDescription("Monthly salary");
        income.setCurrency("USD");
        income.setCreatedAt(now);
        income.setUpdateAt(now);
    }

    @Test
    void createIncome_ShouldReturnCreatedIncome() throws Exception {
        when(incomeService.createIncome(any(IncomeRequest.class))).thenReturn(income);

        mockMvc.perform(post("/api/incomes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incomeRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nic").value("123456789"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.amount").value(5000.0))
                .andExpect(jsonPath("$.source").value("Salary"))
                .andExpect(jsonPath("$.description").value("Monthly salary"))
                .andExpect(jsonPath("$.currency").value("USD"));
    }

    @Test
    void getIncomeByNic_ShouldReturnIncome() throws Exception {
        when(incomeService.getIncomeByNic("123456789")).thenReturn(ResponseEntity.ok(income));

        mockMvc.perform(get("/api/incomes/123456789"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nic").value("123456789"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.amount").value(5000.0))
                .andExpect(jsonPath("$.source").value("Salary"))
                .andExpect(jsonPath("$.description").value("Monthly salary"))
                .andExpect(jsonPath("$.currency").value("USD"));
    }

    @Test
    void updateIncomeByName_ShouldReturnUpdatedIncome() throws Exception {
        when(incomeService.updateIncomeByName("1", incomeRequest)).thenReturn(ResponseEntity.ok(income));

        mockMvc.perform(put("/api/incomes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incomeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nic").value("123456789"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.amount").value(5000.0))
                .andExpect(jsonPath("$.source").value("Salary"))
                .andExpect(jsonPath("$.description").value("Monthly salary"))
                .andExpect(jsonPath("$.currency").value("USD"));
    }

    @Test
    void deleteIncomeByName_ShouldReturnNoContent() throws Exception {
        when(incomeService.deleteIncomeByName("1")).thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(delete("/api/incomes/1"))
                .andExpect(status().isNoContent());
    }
}