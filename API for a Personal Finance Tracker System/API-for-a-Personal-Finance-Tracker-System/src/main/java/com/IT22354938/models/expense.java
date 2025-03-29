package com.IT22354938.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data // Generates Getters, Setters, toString(), equals(), and hashCode()
@NoArgsConstructor // Generates a no-argument constructor
@Document(collection = "expenses") // For MongoDB
@JsonInclude(JsonInclude.Include.NON_NULL) // Include only non-null fields in the response

public class expense {

    @Id
    @JsonProperty("id") // Renames "nic" to "national_id" in JSON
    private String id;  // String for MongoDB; use Long for SQL databases
    @JsonProperty("national_id") // Renames "nic" to "national_id" in JSON
    private String nic;
    @JsonProperty("name_of_expense") // Renames "nic" to "national_id" in JSON
    private String name;
    @JsonProperty("category") // Renames "nic" to "national_id" in JSON
    private String category;
    @JsonProperty("amount") // Renames "nic" to "national_id" in JSON
    private Double amount;
    @JsonProperty("expenseDate") // Renames "nic" to "national_id" in JSON
    private Date expenseDate;
    @JsonProperty("recurring") // Renames "nic" to "national_id" in JSON
    private Boolean recurring;
    @JsonProperty("recurrenceType") // Renames "nic" to "national_id" in JSON
    private String recurrenceType;
    @JsonProperty("paymentMethod") // Renames "nic" to "national_id" in JSON
    private String paymentMethod;
    @JsonProperty("notes") // Renames "nic" to "national_id" in JSON
    private String notes;
    @JsonProperty("currency") // Renames "nic" to "national_id" in JSON
    private String currency;
    @JsonProperty("createdAt") // Renames "nic" to "national_id" in JSON
    private Date createdAt;

    public void setId(String id) {
        this.id = id;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    public void setRecurring(Boolean recurring) {
        this.recurring = recurring;
    }

    public void setRecurrenceType(String recurrenceType) {
        this.recurrenceType = recurrenceType;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    public String getCurrency() {
        return currency;
    }

    public Double getAmount() {
        return amount;
    }

    public expense(String id, String nic, String name, String category, Double amount, Date expenseDate, Boolean recurring, String recurrenceType, String paymentMethod, String notes, String currency, Date createdAt) {
        this.id=id;
        this.nic = nic;
        this.name =name;
        this.category =category;
        this.amount = amount;
        this.expenseDate = expenseDate; // Change 'date' to 'expenseDate'
        this.recurring = recurring;
        this.recurrenceType = recurrenceType;
        this.paymentMethod = paymentMethod;
        this.notes = notes;
        this.currency = currency;
        this.createdAt = createdAt;
    }
}
