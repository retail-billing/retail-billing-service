package com.ashish.retailbillingservice.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String email;
    private String role;

    public static AuthResponse from(String token, String email, String role) {
        return new AuthResponse(token, email, role);
    }
}
