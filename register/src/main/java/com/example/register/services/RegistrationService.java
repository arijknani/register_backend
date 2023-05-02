package com.example.register.services;


import com.example.register.models.ConfirmationToken;
import com.example.register.models.CustomUserDetails;
import com.example.register.models.RegistrationRequest;
import com.example.register.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private User user;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest request) {

    return customUserDetailsService.signUpUser(
            new CustomUserDetails(
                    request.getFirstName(),
                    request.getLastName(),
                    request.getEmail(),
                    request.getPassword()
            )
    );
    }


    public String confirmToken(String token){
        return"confirm";
    }
}
