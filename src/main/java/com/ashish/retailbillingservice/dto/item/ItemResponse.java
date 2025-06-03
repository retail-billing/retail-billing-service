package com.ashish.retailbillingservice.dto.item;

import com.ashish.retailbillingservice.entity.ItemEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemResponse {

    private String itemId;
    private String name;
    private String description;
    private String categoryId;
    private String categoryName;
    private double price;
    private int quantity;
    private String imageUrl;

    public static ItemResponse from(ItemEntity entity) {
        return ItemResponse.builder()
                .itemId(entity.getItemId())
                .name(entity.getName())
                .description(entity.getDescription())
                .categoryId(entity.getCategory().getCategoryId())
                .categoryName(entity.getCategory().getName())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .imageUrl(entity.getImageUrl())
                .build();
    }
}
