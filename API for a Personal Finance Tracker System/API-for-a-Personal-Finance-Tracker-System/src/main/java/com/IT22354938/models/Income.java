package com.IT22354938.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data // Generates Getters, Setters, toString(), equals(), and hashCode()
@Document(collection = "incomes") // For MongoDB
@JsonInclude(JsonInclude.Include.NON_NULL) // Include only non-null fields in the response

public class Income {

    @Id
    @JsonProperty("id") // Renames "nic" to "national_id" in JSON
    private String id;  // String for MongoDB; use Long for SQL databases

    @JsonProperty("national_id") // Renames "nic" to "national_id" in JSON
    private String nic;
    @JsonProperty("full_name") // Renames "name" to "full_name" in JSON
    private String name;
    @JsonProperty("amount") // Renames "name" to "full_name" in JSON
    private Double amount;
    @JsonProperty("source") // Renames "name" to "full_name" in JSON
    private String source;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date incomeDate;
    @JsonProperty("description") // Renames "name" to "full_name" in JSON
    private String description;
    @JsonProperty("currency") // Renames "name" to "full_name" in JSON
    private String currency;

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setIncomeDate(Date incomeDate) {
        this.incomeDate = incomeDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date updateAt;

    public Income(String id, String nic, String name, Double amount, String source, Date incomeDate, String description, String currency, Date createdAt, Date updateAt) {
        this.id = id;
        this.nic = nic;
        this.name = name;
        this.amount = amount;
        this.source = source;
        this.incomeDate = incomeDate;
        this.description = description;
        this.currency = currency;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

    //I created this used to Integration testing
    public Income(){}
}
