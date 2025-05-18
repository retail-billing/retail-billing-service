package com.ashish.retailbillingservice.dto;

import com.ashish.retailbillingservice.entity.CategoryEntity;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class CategoryResponse {

    private String categoryId;
    private String name;
    private String description;
    private String bgColor;
    private String imageUrl;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public static CategoryResponse from(CategoryEntity entity) {
        return CategoryResponse.builder()
                .categoryId(entity.getCategoryId())
                .name(entity.getName())
                .description(entity.getDescription())
                .bgColor(entity.getBgColor())
                .imageUrl(entity.getImageUrl())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
