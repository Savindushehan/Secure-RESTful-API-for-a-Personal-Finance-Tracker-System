package com.IT22354938.service;

import com.IT22354938.dto.IncomeRequest;
import com.IT22354938.dto.UserRequest;
import com.IT22354938.models.Income;
import com.IT22354938.models.User;
import com.IT22354938.repository.IncomeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public Income createIncome(IncomeRequest incomeRequest) {
        Income income = new Income(
                incomeRequest.id(), // MongoDB will auto-generate the ID
                incomeRequest.nic(),
                incomeRequest.name(),
                incomeRequest.amount(),
                incomeRequest.source(),
                incomeRequest.incomeDate(),
                incomeRequest.description(),
                incomeRequest.currency(),
                incomeRequest.createdAt(),
                incomeRequest.updateAt()
        );

        return incomeRepository.save(income);
    }

    public ResponseEntity<Income> getIncomeByNic(String nic) {
        System.out.println("Fetching income for NIC: " + nic);
        Optional<Income> income = incomeRepository.findByNic(nic);
        System.out.println("Result: " + income);
        return income.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    // Update Income by Name
    public ResponseEntity<Income> updateIncomeByName(String id, IncomeRequest incomeRequest) {
        Optional<Income> optionalIncome = incomeRepository.findById(id);

        if (optionalIncome.isPresent()) {
            Income income = optionalIncome.get();
            income.setAmount(incomeRequest.amount());
            income.setSource(incomeRequest.source());
            income.setIncomeDate(incomeRequest.incomeDate());
            income.setDescription(incomeRequest.description());
            income.setCurrency(incomeRequest.currency());
            income.setUpdateAt(incomeRequest.updateAt());

            return ResponseEntity.ok(incomeRepository.save(income));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete Income by Name
    public ResponseEntity<Void> deleteIncomeByName(String id) {
        Optional<Income> optionalIncome = incomeRepository.findById(id);

        if (optionalIncome.isPresent()) {
            incomeRepository.delete(optionalIncome.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
