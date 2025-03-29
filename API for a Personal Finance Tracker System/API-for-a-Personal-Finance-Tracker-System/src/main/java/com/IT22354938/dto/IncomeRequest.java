package com.IT22354938.dto;

import lombok.Data;

import java.util.Date;

public record IncomeRequest(String id,String nic, String name, Double amount, String source, Date incomeDate, String description, String currency, Date createdAt, Date updateAt) {
}
