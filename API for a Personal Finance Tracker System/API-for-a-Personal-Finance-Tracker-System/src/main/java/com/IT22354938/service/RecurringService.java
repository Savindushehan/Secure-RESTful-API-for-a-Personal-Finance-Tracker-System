package com.IT22354938.service;

import com.IT22354938.dto.CategoryRequest;
import com.IT22354938.dto.IncomeRequest;
import com.IT22354938.dto.RecurringRequest;
import com.IT22354938.models.Category;
import com.IT22354938.models.Income;
import com.IT22354938.models.recurring;
import com.IT22354938.repository.RecurringRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecurringService {

    private final RecurringRepository recurringRepository;

    public RecurringService(RecurringRepository recurringRepository) {
        this.recurringRepository = recurringRepository;
    }

    public recurring createRecurring(RecurringRequest recurringRequest) {


        recurring recurring = new recurring(
                recurringRequest.id(),
                recurringRequest.nic(),
                recurringRequest.name(),
                recurringRequest.recurrenceType(),
                recurringRequest.expenseDate()
        );
        return recurringRepository.save(recurring);
    }



    public recurring updateRecurring(String id, RecurringRequest recurringRequest) {
        return recurringRepository.findById(id)
                .map(existingRecurring -> {
                    existingRecurring.setNic(recurringRequest.nic());
                    existingRecurring.setName(recurringRequest.name());
                    existingRecurring.setRecurrenceType(recurringRequest.recurrenceType());
                    existingRecurring.setExpenseDate(recurringRequest.expenseDate());
                    return recurringRepository.save(existingRecurring);
                })
                .orElseThrow(() -> new RuntimeException("Recurring not found with id: " + id));
    }

    public void deleteRecurring(String id) {
        recurringRepository.deleteById(id);
    }
    public Optional<recurring> getRecurringById(String id) {
        return recurringRepository.findById(id);
    }

}
