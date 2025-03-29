package com.IT22354938.controller;



import com.IT22354938.dto.CategoryRequest;
import com.IT22354938.models.Category;
import com.IT22354938.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
public class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;  // For sending HTTP requests to the controller

    @Autowired
    private CategoryRepository categoryRepository;  // To interact with the database during the test

    private CategoryRequest categoryRequest;
    private Category category;

    @BeforeEach
    void setUp() {
        categoryRequest = new CategoryRequest("1", "Income", "Travel", new Date());
        category = new Category("1", "Income", "Travel", new Date());
        categoryRepository.save(category);  // Save category in DB for testing
    }

    @Test
    void testCreateCategory() throws Exception {
        CategoryRequest newCategoryRequest = new CategoryRequest("2", "Expense", "Food", new Date());

        // Perform a POST request to create a new category
        mockMvc.perform(post("/api/categories")
                        .contentType("application/json")
                        .content("{\"id\":\"2\",\"categoryType\":\"Expense\",\"categoryName\":\"Food\",\"createdDate\":\"2025-03-10\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryType").value("Expense"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").value("Food"));
    }

    @Test
    void testGetAllCategories() throws Exception {
        // Perform a GET request to fetch all categories
        mockMvc.perform(get("/api/categories"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].categoryName").value("Travel"));
    }

    @Test
    void testGetCategoryById() throws Exception {
        String categoryId = "1";

        // Perform a GET request to fetch the category by ID
        mockMvc.perform(get("/api/categories/{id}", categoryId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").value("Travel"));
    }

    @Test
    void testUpdateCategory() throws Exception {
        String categoryId = "1";

        // Perform a PUT request to update the category
        mockMvc.perform(put("/api/categories/{id}", categoryId)
                        .contentType("application/json")
                        .content("{\"categoryType\":\"Expense\",\"categoryName\":\"Food\",\"createdDate\":\"2025-03-10\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").value("Food"));
    }

    @Test
    void testDeleteCategory() throws Exception {
        String categoryId = "1";

        // Perform a DELETE request to delete the category by ID
        mockMvc.perform(delete("/api/categories/{id}", categoryId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify the category is deleted
        List<Category> categories = categoryRepository.findAll();
        assert categories.stream().noneMatch(c -> c.getId().equals(categoryId));
    }
}

