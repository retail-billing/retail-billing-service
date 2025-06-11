package com.ashish.retailbillingservice.dto.user;

import com.ashish.retailbillingservice.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.ashish.retailbillingservice.exception.BadRequestException;
import static io.micrometer.common.util.StringUtils.isBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String role;

    public void validate() {
        if (isBlank(name)) {
            throw new BadRequestException("Name cannot be blank");
        }
        if (isBlank(email)) {
            throw new BadRequestException("Email cannot be blank");
        }
        if (isBlank(password)) {
            throw new BadRequestException("Password cannot be blank");
        }
        if(isBlank(phoneNumber)) {
            throw new BadRequestException("Phone number cannot be blank");
        }
        if (isBlank(role)) {
            throw new BadRequestException("Role cannot be blank");
        }
        if (!UserRole.isValid(role)) {
            throw new BadRequestException("Role must be either 'USER' or 'ADMIN'");
        }
    }
}
