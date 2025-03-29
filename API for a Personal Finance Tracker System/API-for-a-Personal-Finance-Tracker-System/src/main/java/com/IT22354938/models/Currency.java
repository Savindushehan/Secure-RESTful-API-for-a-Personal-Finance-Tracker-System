package com.IT22354938.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document(collection = "currencies")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Currency {
    @Id
    @JsonProperty("id")
    private String id;  // MongoDB uses String ID instead of Long

    @JsonProperty("code")
    private String code;

    @JsonProperty("exchangeRate")
    private BigDecimal exchangeRate;

    // Getter for exchangeRate (Lombok will automatically generate this)

    public void setCode(String code) {
        this.code = code;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }
}
