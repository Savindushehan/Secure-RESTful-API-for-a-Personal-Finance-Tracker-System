package com.IT22354938.service;

import com.IT22354938.dto.EmailRequest;
import com.IT22354938.dto.ExpenseRequest;
import com.IT22354938.models.Budget;
import com.IT22354938.models.Category;
import com.IT22354938.models.expense;
import com.IT22354938.repository.ExpenseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final BudgetService budgetService; // Inject BudgetService to check budget
    private final CategoryService categoryService;
    EmailService emailService;
    private final CurrencyService currencyService; // Inject CurrencyService


    public ExpenseService(ExpenseRepository expenseRepository, BudgetService budgetService, CategoryService categoryService, CurrencyService currencyService) {
        this.expenseRepository = expenseRepository;
        this.budgetService = budgetService;
        this.categoryService = categoryService;
        this.currencyService = currencyService;
    }

//    public expense createExpense(ExpenseRequest expenseRequest){
//        expense expense = new expense(
//                expenseRequest.id(),
//                expenseRequest.nic(),
//                expenseRequest.name(),
//                expenseRequest.category(),
//                expenseRequest.amount(),
//                expenseRequest.expenseDate(),
//                expenseRequest.recurring(),
//                expenseRequest.recurrenceType(),
//                expenseRequest.paymentMethod(),
//                expenseRequest.notes(),
//                expenseRequest.currency(),
//                expenseRequest.createdAt()
//        );
//        return expenseRepository.save(expense);
//    }
public ResponseEntity<?> createExpense(ExpenseRequest expenseRequest) {

    ResponseEntity<List<Category>> categoryResponse = categoryService.getCategoryByCategoryTypeAndCategoryName("Expense", expenseRequest.category());

    // Check if the category exists and is valid
    if (categoryResponse.getBody() == null || categoryResponse.getBody().isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid or non-existent category for Expense.");
    }



    // Fetch the budget using NIC & Category
    ResponseEntity<List<Budget>> response = budgetService.getBudgetByNICandCategory(expenseRequest.nic(), expenseRequest.category());

//    if (response.getBody() == null || response.getBody().isEmpty()) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body("No budget found for the given NIC and category!");
//    }

    if (response.getBody() == null || response.getBody().isEmpty()) {
        response = budgetService.getBudgetByNICandMonth(expenseRequest.nic(), expenseRequest.expenseDate().getMonth());

    }
    Budget budget = response.getBody().get(0);



    // Calculate remaining budget
    double remainingBudget = budget.getAmount() - expenseRequest.amount();

    if (remainingBudget < 0) {
        EmailRequest emailRequest = new EmailRequest(
                "s.m.savindushehan@gmail.com",  // Email recipient
                "Insufficient Budget Alert",  // Subject
                "Your current budget is insufficient to add this expense. Remaining budget: " + remainingBudget  // Body
        );

        // Send the email
      //  String emailResponse = emailService.sendEmail(emailRequest);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Insufficient budget! Cannot add this expense.");
    }else {
        // Update the budget with the remaining balance

//        if (remainingBudget <= 1000) {
//            System.out.println("Budget is less than 1000 now. Please reduce the expenses.");
//        }

        int month= expenseRequest.expenseDate().getMonth()+1;
        ResponseEntity<Budget> updatedBudgetResponse = budgetService.updateBudgetAmount(
                expenseRequest.nic(),
                month,
                expenseRequest.category(), // Add the missing budgetType parameter
                remainingBudget
        );


        if (!updatedBudgetResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update the budget.");
        }
    }


    // Save the expense
    expense newExpense = new expense(
            expenseRequest.id(),
            expenseRequest.nic(),
            expenseRequest.name(),
            expenseRequest.category(),
            expenseRequest.amount(),
            expenseRequest.expenseDate(),
            expenseRequest.recurring(),
            expenseRequest.recurrenceType(),
            expenseRequest.paymentMethod(),
            expenseRequest.notes(),
            expenseRequest.currency(),
            expenseRequest.createdAt()
    );

    expense savedExpense = expenseRepository.save(newExpense);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);
}


    public ResponseEntity<expense> getExpenseByNIC(String nic){
        System.out.println("Fetching expense for NIC: " + nic);
        Optional<expense> income = expenseRepository.findByNic(nic);
        System.out.println("Result: " + income);
        return income.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    public ResponseEntity<expense> updateExpenseByName(String id, ExpenseRequest expenseRequest) {
        Optional<expense> optionalExpense = expenseRepository.findById(id);

        if (optionalExpense.isPresent()) {
            expense expense = optionalExpense.get();
            expense.setName(expenseRequest.name());
            expense.setCategory(expenseRequest.category());
            expense.setAmount(expenseRequest.amount());
            expense.setExpenseDate(expenseRequest.expenseDate());
            expense.setRecurring(expenseRequest.recurring());
            expense.setRecurrenceType(expenseRequest.recurrenceType());
            expense.setPaymentMethod(expenseRequest.paymentMethod());
            expense.setNotes(expenseRequest.notes());
            expense.setCurrency(expenseRequest.currency());
            expense.setCreatedAt(expenseRequest.createdAt());



            return ResponseEntity.ok(expenseRepository.save(expense));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Void> deleteExpenseByName(String id) {
        Optional<expense> optionalExpense = expenseRepository.findById(id);

        if (optionalExpense.isPresent()) {
            expenseRepository.delete(optionalExpense.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<expense>> getExpensesByCategory(String category) {
        List<expense> expenses = expenseRepository.findByCategory(category);
        return ResponseEntity.ok(expenses);
    }

    public ResponseEntity<List<expense>> getAllExpenses() {
        List<expense> expenses = expenseRepository.findAll();

        for (expense exp : expenses) {
            String expenseCurrency = exp.getCurrency(); // Get the currency from expense

            // If the currency exists, fetch the exchange rate for that currency
            if (expenseCurrency != null) {
                // Assuming the target currency is 'USD' (change this to your desired target currency)
                Double amount = exp.getAmount() * currencyService.getExchangeRateByCode(expenseCurrency).doubleValue();


//                BigDecimal exchangeRate = currencyService.convertCurrency(
//                        BigDecimal.valueOf(exp.getAmount()),
//                        expenseCurrency,
//                        targetCurrency
//                );

                // Convert the amount to the target currency
                exp.setAmount(amount);
                exp.setCurrency(expenseCurrency); // Update the currency to the target currency (e.g., USD)
            }
        }

        return ResponseEntity.ok(expenses);
    }

}
