package com.ashish.retailbillingservice.controller;

import com.ashish.retailbillingservice.dto.category.CategoryRequest;
import com.ashish.retailbillingservice.dto.category.CategoryResponse;
import com.ashish.retailbillingservice.exception.BadRequestException;
import com.ashish.retailbillingservice.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ObjectMapper objectMapper;

    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryResponse> add(
            @RequestPart String categoryRequestJson,
            @RequestPart MultipartFile file
    ) {
        try {
            CategoryRequest categoryRequest = objectMapper.readValue(categoryRequestJson, CategoryRequest.class);
            categoryRequest.validate();
            CategoryResponse response = categoryService.addCategory(categoryRequest, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> fetchAll() {
        List<CategoryResponse> categories = categoryService.fetchCategories();
        return ResponseEntity.ok(categories);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<Void> remove(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
