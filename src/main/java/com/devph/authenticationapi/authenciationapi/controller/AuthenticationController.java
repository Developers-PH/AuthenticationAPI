package com.devph.authenticationapi.authenciationapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devph.authenticationapi.authenciationapi.models.dto.SigninRequestDto;
import com.devph.authenticationapi.authenciationapi.models.dto.SignupRequestDto;
import com.devph.authenticationapi.authenciationapi.service.AuthenticationService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninRequestDto loginRequest) {

        return authenticationService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@Valid @RequestBody SignupRequestDto signUpRequest) {

        return authenticationService.signupUser(signUpRequest);
    }

}
