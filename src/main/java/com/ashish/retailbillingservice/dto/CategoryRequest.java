package com.ashish.retailbillingservice.dto;

import com.ashish.retailbillingservice.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.micrometer.common.util.StringUtils.isBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    private String name;
    private String description;
    private String bgColor;

    public void validate() throws BadRequestException {
        if(isBlank(name)) {
            throw new BadRequestException("Name cannot be blank");
        }
    }
}
