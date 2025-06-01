package com.ashish.retailbillingservice.controller;

import com.ashish.retailbillingservice.dto.auth.AuthRequest;
import com.ashish.retailbillingservice.dto.auth.AuthResponse;
import com.ashish.retailbillingservice.service.ApplicationUserDetailsService;
import com.ashish.retailbillingservice.service.UserService;
import com.ashish.retailbillingservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final ApplicationUserDetailsService applicationUserDetailsService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        authenticate(authRequest);
        UserDetails userDetails = applicationUserDetailsService.loadUserByUsername(authRequest.getEmail());
        String jwtToken = jwtUtil.generateToken(userDetails);
        String userRole = userService.getUserRole(authRequest.getEmail());
        AuthResponse response = AuthResponse.from(jwtToken, authRequest.getEmail(), userRole);
        return ResponseEntity.ok(response);
    }

    private void authenticate(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()));
        } catch (DisabledException e) {
            throw new RuntimeException("User is disabled", e);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password", e);
        }
    }

    @PostMapping("/encode")
    public ResponseEntity<String> encodePassword(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(passwordEncoder.encode(request.get("password")));
    }
}
