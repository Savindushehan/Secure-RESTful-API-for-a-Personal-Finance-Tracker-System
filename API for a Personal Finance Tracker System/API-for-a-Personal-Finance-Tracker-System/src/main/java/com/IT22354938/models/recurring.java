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
@Document(collection = "recurring") // For MongoDB
@JsonInclude(JsonInclude.Include.NON_NULL) // Include only non-null fields in the response
public class recurring {

    @Id
    @JsonProperty("id")
    private String id;
    @JsonProperty("national_id") // Renames "nic" to "national_id" in JSON
    private String nic;
    @JsonProperty("name_of_expense") // Renames "nic" to "national_id" in JSON
    private String name;
    @JsonProperty("recurrenceType") // Renames "nic" to "national_id" in JSON
    private String recurrenceType;
    @JsonProperty("expenseDate") // Renames "nic" to "national_id" in JSON
    private Date expenseDate;


    public recurring(String id, String nic,String name, String recurrenceType, Date expenseDate) {
        this.id = id;
        this.nic = nic;
        this.recurrenceType = recurrenceType;
        this.expenseDate = expenseDate;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getRecurrenceType() {
        return recurrenceType;
    }

    public void setRecurrenceType(String recurrenceType) {
        this.recurrenceType = recurrenceType;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    public void setName(String name) {
        this.name = name;
    }
}
