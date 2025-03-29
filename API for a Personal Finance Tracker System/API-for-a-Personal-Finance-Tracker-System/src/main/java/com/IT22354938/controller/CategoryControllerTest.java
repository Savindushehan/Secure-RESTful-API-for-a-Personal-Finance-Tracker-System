package com.IT22354938.controller;

import com.IT22354938.dto.CategoryRequest;
import com.IT22354938.models.Category;
import com.IT22354938.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private CategoryRequest categoryRequest;
    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create a valid Date object for testing
        Date date = new Date();

        // Initialize the CategoryRequest and Category with the Date
        categoryRequest = new CategoryRequest("1", "Income", "Travel", date);
        category = new Category("1", "Income", "Travel", date);
    }

    @Test
    void testCreateCategory() {
        when(categoryService.createCategory(categoryRequest)).thenReturn(category);

        ResponseEntity<?> response = categoryController.createCategory(categoryRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(category, response.getBody());
        verify(categoryService, times(1)).createCategory(categoryRequest);
    }

    @Test
    void testGetAllCategories() {
        when(categoryService.getAllCategories()).thenReturn(List.of(category));

        List<Category> response = categoryController.getAllCategories();

        assertEquals(1, response.size());
        assertEquals(category, response.get(0));
        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void testGetCategoryById() {
        String id = "1";
        when(categoryService.getCategoryById(id)).thenReturn(Optional.of(category));

        Optional<Category> response = categoryController.getCategoryById(id);

        assertTrue(response.isPresent());
        assertEquals(category, response.get());
        verify(categoryService, times(1)).getCategoryById(id);
    }

    @Test
    void testUpdateCategory() {
        String id = "1";
        when(categoryService.updateCategory(id, categoryRequest)).thenReturn(category);

        Category response = categoryController.updateCategory(id, categoryRequest);

        assertEquals(category, response);
        verify(categoryService, times(1)).updateCategory(id, categoryRequest);
    }

    @Test
    void testDeleteCategory() {
        String id = "1";
        doNothing().when(categoryService).deleteCategory(id);

        categoryController.deleteCategory(id);

        verify(categoryService, times(1)).deleteCategory(id);
    }
}
