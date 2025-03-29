package com.IT22354938.controller;

import com.IT22354938.models.Currency;  // Use your custom Currency model
import com.IT22354938.repository.CurrencyRepository;
import com.IT22354938.service.CurrencyService;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {
    private final CurrencyService currencyService;
    private final CurrencyRepository currencyRepository;

    // Constructor
    public CurrencyController(CurrencyService currencyService, CurrencyRepository currencyRepository) {
        this.currencyService = currencyService;
        this.currencyRepository = currencyRepository;
    }

    // Add new Currency
    @PostMapping("/add")
    public Currency addCurrency(@RequestBody Currency currency) {
        return currencyRepository.save(currency);
    }

    // Convert Currency
    @GetMapping("/convert")
    public BigDecimal convertCurrency(
            @RequestParam BigDecimal amount,
            @RequestParam String from,
            @RequestParam String to) {
        return currencyService.convertCurrency(amount, from, to);
    }

    // Get all Currencies
    @GetMapping("/all")
    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }
}
