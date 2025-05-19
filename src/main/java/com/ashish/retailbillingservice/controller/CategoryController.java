package com.ashish.retailbillingservice.controller;

import com.ashish.retailbillingservice.dto.CategoryRequest;
import com.ashish.retailbillingservice.dto.CategoryResponse;
import com.ashish.retailbillingservice.exception.BadRequestException;
import com.ashish.retailbillingservice.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ObjectMapper objectMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse add(
            @RequestPart String categoryRequestJson,
            @RequestPart MultipartFile file
    ) {
        try {
            CategoryRequest categoryRequest = objectMapper.readValue(categoryRequestJson, CategoryRequest.class);
            categoryRequest.validate();
            return categoryService.addCategory(categoryRequest, file);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse> fetchAll() {
        return categoryService.fetchCategories();
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
