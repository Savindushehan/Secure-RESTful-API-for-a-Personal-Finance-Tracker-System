package com.IT22354938.dto;

import java.util.Date;

public record RecurringRequest(String id, String nic,String name, String recurrenceType, Date expenseDate) {
}
