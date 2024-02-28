package com.devph.authenticationapi.authenciationapi.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devph.authenticationapi.authenciationapi.config.jwt.JwtUtils;
import com.devph.authenticationapi.authenciationapi.models.dto.SigninRequestDto;
import com.devph.authenticationapi.authenciationapi.models.dto.SigninResponseDto;
import com.devph.authenticationapi.authenciationapi.models.dto.SignupRequestDto;
import com.devph.authenticationapi.authenciationapi.models.dto.SignupResponseDto;
import com.devph.authenticationapi.authenciationapi.models.entity.EnumRole;
import com.devph.authenticationapi.authenciationapi.models.entity.Role;
import com.devph.authenticationapi.authenciationapi.models.entity.User;
import com.devph.authenticationapi.authenciationapi.repository.RolesRepository;
import com.devph.authenticationapi.authenciationapi.repository.UserRepository;
import com.devph.authenticationapi.authenciationapi.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public ResponseEntity<?> authenticateUser(SigninRequestDto signinBody) {
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(signinBody.getUsername(), signinBody.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(new SigninResponseDto(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(),
                        roles));
    }

    @Override
    public ResponseEntity<?> signupUser(SignupRequestDto signupBody) {
        if (userRepository.existsByUsername(signupBody.getUsername())) {
            return ResponseEntity.badRequest().body(new SignupResponseDto("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signupBody.getEmail())) {
            return ResponseEntity.badRequest().body(new SignupResponseDto("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signupBody.getUsername(), signupBody.getEmail(),
                encoder.encode(signupBody.getPassword()));

        Set<String> strRoles = signupBody.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(EnumRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(EnumRole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(EnumRole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(EnumRole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new SignupResponseDto("User registered successfully!"));

    }

}
