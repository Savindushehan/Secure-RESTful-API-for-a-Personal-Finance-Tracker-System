package com.IT22354938.repository;

import com.IT22354938.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String> {
    List<Category> findByCategoryTypeAndCategoryName(String categoryType, String categoryName);
}
