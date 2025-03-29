package com.IT22354938.dto;

import java.util.Date;

public record BudgetRequest(String id, String nic, String budgetType , String month, String category, Double amount, Date createdAt) {
}

