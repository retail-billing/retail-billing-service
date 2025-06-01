package com.ashish.retailbillingservice.controller;

import com.ashish.retailbillingservice.dto.user.UserRequest;
import com.ashish.retailbillingservice.dto.user.UserResponse;
import com.ashish.retailbillingservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest request) {
        request.validate();
        return ResponseEntity.ok(userService.createUser(request));
    }

    @GetMapping("/role")
    public ResponseEntity<String> getUserRole(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserRole(email));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> readUsers() {
        return ResponseEntity.ok(userService.readUsers());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}