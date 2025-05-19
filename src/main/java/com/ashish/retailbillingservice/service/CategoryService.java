package com.ashish.retailbillingservice.service;

import com.ashish.retailbillingservice.dto.CategoryRequest;
import com.ashish.retailbillingservice.dto.CategoryResponse;
import com.ashish.retailbillingservice.entity.CategoryEntity;
import com.ashish.retailbillingservice.exception.NotFoundException;
import com.ashish.retailbillingservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final FileUploadService fileUploadService;
    private final CategoryRepository categoryRepository;

    public CategoryResponse addCategory(CategoryRequest categoryRequest, MultipartFile file) {
        CategoryEntity newCategory = CategoryEntity.from(categoryRequest);
        String imageUrl = fileUploadService.uploadFile(file);
        newCategory.setImageUrl(imageUrl);
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
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findByCategoryId(categoryId);
        optionalCategoryEntity.ifPresentOrElse(categoryEntity -> {
            categoryRepository.delete(categoryEntity);
            fileUploadService.deleteFile(categoryEntity.getImageUrl());
        }, () -> {
                    throw new NotFoundException("Category not found");
                });
    }
}
