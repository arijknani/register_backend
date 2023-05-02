package com.example.register.services;

import com.example.register.models.ConfirmationToken;
import com.example.register.models.CustomUserDetails;
import com.example.register.models.User;
import com.example.register.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {



    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }

    public String signUpUser(CustomUserDetails customUserDetails){
        User user = new User();
        String encodedPassword = passwordEncoder()
                .encode(customUserDetails.getPassword());
        User existingUser = userRepo

                .findByEmail(customUserDetails.getUsername());
        if (existingUser != null) {
            // user with given email already exists
            return "User with email " + customUserDetails.getUsername() + " already exists.";
        }
        user.setFirstName(customUserDetails.getFirstName());
        user.setLastName(customUserDetails.getLastName());
        user.setEmail(customUserDetails.getUsername());
        user.setPassword(encodedPassword);
        userRepo.save(user);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(

                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

}
