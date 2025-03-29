package com.IT22354938.controller;
import com.IT22354938.dto.RecurringRequest;
import com.IT22354938.models.recurring;
import com.IT22354938.service.RecurringService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/recurring")
public class RecurringController {

    private final RecurringService recurringService;

    public RecurringController(RecurringService recurringService) {
        this.recurringService = recurringService;
    }

    @PostMapping
    public ResponseEntity<recurring> createRecurring(@RequestBody RecurringRequest recurringRequest) {
        recurring newRecurring = recurringService.createRecurring(recurringRequest);
        return ResponseEntity.ok(newRecurring);
    }

    @PutMapping("/{id}")
    public ResponseEntity<recurring> updateRecurring(@PathVariable String id, @RequestBody RecurringRequest recurringRequest) {
        recurring updatedRecurring = recurringService.updateRecurring(id, recurringRequest);
        return ResponseEntity.ok(updatedRecurring);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecurring(@PathVariable String id) {
        recurringService.deleteRecurring(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<recurring> getRecurringById(@PathVariable String id) {
        Optional<recurring> recurringOptional = recurringService.getRecurringById(id);
        return recurringOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

