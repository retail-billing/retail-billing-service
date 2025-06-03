package com.ashish.retailbillingservice.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.micrometer.common.util.StringUtils.isBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {

    private String name;
    private String description;
    private String categoryId;
    private Double price;
    private Integer quantity;
    private String imageUrl;

    public void validate() {
        if (isBlank(name)) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (isBlank(description)) {
            throw new IllegalArgumentException("Description cannot be blank");
        }
        if (isBlank(categoryId)) {
            throw new IllegalArgumentException("Category ID cannot be blank");
        }
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("Price cannot be null or less than or equal to zero");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity cannot be null or less than or equal to zero");
        }
    }
}
