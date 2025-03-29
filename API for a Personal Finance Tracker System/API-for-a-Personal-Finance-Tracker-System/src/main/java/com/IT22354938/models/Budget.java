package com.IT22354938.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.util.Date;

public class Budget {

    @Id
    @JsonProperty("id") // Renames "nic" to "national_id" in JSON
    private String id;  // String for MongoDB; use Long for SQL databases
    @JsonProperty("national_id")
    private String nic;
    @JsonProperty("budget_type")
    private String budgetType;
    @JsonProperty("month")
    private String month;
    @JsonProperty("category")
    private String category;
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("createdAt")
    private Date createdAt;

    public Budget(String id, String nic, String budgetType, String month, String category, Double amount, Date createdAt) {
        this.id = id;
        this.nic = nic;
        this.budgetType = budgetType;
        this.month = month;
        this.category = category;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public Budget(){}

    public void setId(String id) {
        this.id = id;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Double getAmount() {
        return amount;
    }
}
