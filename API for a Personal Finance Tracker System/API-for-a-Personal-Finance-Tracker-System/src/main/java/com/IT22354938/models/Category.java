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
@Document(collection = "category") // For MongoDB
@JsonInclude(JsonInclude.Include.NON_NULL) // Include only non-null fields in the response
public class Category {

    @Id
    @JsonProperty("id")
    private String id;
    @JsonProperty("categoryType")
    private String categoryType;
    @JsonProperty("categoryName")
    private String categoryName;
    @JsonProperty("createdDate")
    private Date createdDate;

    public Category(String id, String categoryType, String categoryName, Date createdDate) {
        this.id = id;
        this.categoryType = categoryType;
        this.categoryName = categoryName;
        this.createdDate = createdDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
}
