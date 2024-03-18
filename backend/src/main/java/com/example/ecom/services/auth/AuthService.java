package com.example.ecom.services.auth;

import com.example.ecom.dto.SignupRequest;
import com.example.ecom.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);
    Boolean hasUserWithEmail(String email);
}
