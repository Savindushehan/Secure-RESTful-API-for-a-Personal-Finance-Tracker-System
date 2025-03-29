package com.IT22354938.repository;

import com.IT22354938.models.Currency;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface CurrencyRepository extends MongoRepository<Currency, String> {
    Optional<Currency> findByCode(String code);
}
