package com.ashish.retailbillingservice.dto.user;

import com.ashish.retailbillingservice.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private String userId;
    private String name;
    private String email;
    private String role;
    private String createdAt;
    private String updatedAt;

    public static UserResponse from(UserEntity userEntity) {
        return UserResponse.builder()
                .userId(userEntity.getUserId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .createdAt(userEntity.getCreatedAt().toString())
                .updatedAt(userEntity.getUpdatedAt().toString())
                .build();
    }
}
