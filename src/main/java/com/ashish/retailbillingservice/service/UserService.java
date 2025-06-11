package com.ashish.retailbillingservice.service;

import com.ashish.retailbillingservice.dto.user.UserRequest;
import com.ashish.retailbillingservice.dto.user.UserResponse;
import com.ashish.retailbillingservice.entity.UserEntity;
import com.ashish.retailbillingservice.exception.BadRequestException;
import com.ashish.retailbillingservice.exception.NotFoundException;
import com.ashish.retailbillingservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest request) {
        validateUserEmailAndPhoneNumberIsUnique(request);
        UserEntity user = UserEntity.from(request, passwordEncoder.encode(request.getPassword()));
        return UserResponse.from(userRepository.save(user));
    }

    private void validateUserEmailAndPhoneNumberIsUnique(UserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists");
        }
        if(userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new BadRequestException("Phone number already exists");
        }
    }

    public String getUserRole(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));
        return user.getRole();
    }

    public List<UserResponse> readUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());
    }

    public void deleteUser(String userId) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        userRepository.delete(user);
    }
}
