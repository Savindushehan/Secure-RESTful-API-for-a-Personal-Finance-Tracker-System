package com.IT22354938.dto;

import jakarta.validation.constraints.Pattern;  // ✅ Correct import
import java.util.Date;

public record CategoryRequest(
        String id,
        @Pattern(regexp = "Income|Expense", message = "categoryType must be 'Income' or 'Expense'")
        String categoryType,
        String categoryName,
        Date createdDate
) {}
