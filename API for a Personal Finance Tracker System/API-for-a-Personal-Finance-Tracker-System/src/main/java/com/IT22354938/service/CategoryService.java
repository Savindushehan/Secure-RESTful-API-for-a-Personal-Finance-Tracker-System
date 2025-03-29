package com.IT22354938.service;


import com.IT22354938.dto.CategoryRequest;
import com.IT22354938.models.Budget;
import com.IT22354938.models.Category;
import com.IT22354938.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(CategoryRequest categoryRequest) {
        if (!categoryRequest.categoryType().equals("Income") && !categoryRequest.categoryType().equals("Expense")) {
            throw new IllegalArgumentException("categoryType must be 'Income' or 'Expense'");
        }

        Category category = new Category(
                categoryRequest.id(),
                categoryRequest.categoryType(),
                categoryRequest.categoryName(),
                categoryRequest.createdDate()
        );
        return categoryRepository.save(category);
    }



    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(String id) {
        return categoryRepository.findById(id);
    }

    public Category updateCategory(String id, CategoryRequest categoryRequest) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setCategoryType(categoryRequest.categoryType());
                    existingCategory.setCategoryName(categoryRequest.categoryName());
                    existingCategory.setCreatedDate(categoryRequest.createdDate());
                    return categoryRepository.save(existingCategory);
                })
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }

    public ResponseEntity<List<Category>> getCategoryByCategoryTypeAndCategoryName(String categoryType, String categoryName) {
        System.out.println("Fetching Category for categoryType: " + categoryType + " and Category: " + categoryName);

        List<Category> categories = categoryRepository.findByCategoryTypeAndCategoryName(categoryType, categoryName);

        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(categories);
        }
    }



}
