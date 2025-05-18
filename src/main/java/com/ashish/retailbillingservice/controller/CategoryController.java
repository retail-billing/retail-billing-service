package com.ashish.retailbillingservice.controller;

import com.ashish.retailbillingservice.dto.CategoryRequest;
import com.ashish.retailbillingservice.dto.CategoryResponse;
import com.ashish.retailbillingservice.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return categoryService.addCategory(categoryRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse> fetchCategories() {
        return categoryService.fetchCategories();
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
