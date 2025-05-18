package com.ashish.retailbillingservice.service;

import com.ashish.retailbillingservice.dto.CategoryRequest;
import com.ashish.retailbillingservice.dto.CategoryResponse;
import com.ashish.retailbillingservice.entity.CategoryEntity;
import com.ashish.retailbillingservice.exception.NotFoundException;
import com.ashish.retailbillingservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse addCategory(CategoryRequest categoryRequest) {
        CategoryEntity newCategory = CategoryEntity.from(categoryRequest);
        CategoryEntity categoryEntity = categoryRepository.save(newCategory);
        return CategoryResponse.from(categoryEntity);
    }

    public List<CategoryResponse> fetchCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
    }

    public void deleteCategory(String categoryId) {
        categoryRepository.findByCategoryId(categoryId)
                .ifPresentOrElse(categoryRepository::delete, () -> {
                    throw new NotFoundException("Category not found");
                });
    }
}
