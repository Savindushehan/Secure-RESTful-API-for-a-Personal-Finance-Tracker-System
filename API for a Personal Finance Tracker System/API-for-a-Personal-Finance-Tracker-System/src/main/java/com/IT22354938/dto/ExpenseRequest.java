package com.IT22354938.dto;

import java.util.Date;

public record ExpenseRequest(String id, String nic, String name, String category, Double amount, Date expenseDate,Boolean recurring,
                             String recurrenceType,String paymentMethod,String notes,String currency,Date createdAt) {


}
