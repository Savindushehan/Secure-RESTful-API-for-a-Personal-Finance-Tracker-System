package com.IT22354938.service;

import com.IT22354938.models.Currency;  // Custom Currency model
import com.IT22354938.repository.CurrencyRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public BigDecimal convertCurrency(BigDecimal amount, String fromCurrency, String toCurrency) {
        Optional<Currency> from = currencyRepository.findByCode(fromCurrency);
        Optional<Currency> to = currencyRepository.findByCode(toCurrency);

        if (from.isPresent() && to.isPresent()) {
            BigDecimal baseAmount = amount.divide(from.get().getExchangeRate(), 2, BigDecimal.ROUND_HALF_UP);
            return baseAmount.multiply(to.get().getExchangeRate());
        } else {
            throw new RuntimeException("Currency not found!");
        }
    }

    // Method to get the exchange rate using currency code
    public BigDecimal getExchangeRateByCode(String currencyCode) {
        Optional<Currency> currency = currencyRepository.findByCode(currencyCode);

        if (currency.isPresent()) {
            return currency.get().getExchangeRate();  // Return the exchange rate for the given currency code
        } else {
            throw new RuntimeException("Currency not found for code: " + currencyCode);
        }
    }
}
