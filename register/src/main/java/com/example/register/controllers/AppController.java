package com.example.register.controllers;


import com.example.register.models.RegistrationRequest;
import com.example.register.services.RegistrationService;
import com.example.register.models.User;
import com.example.register.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequestMapping("")//http://localhost:8080
@AllArgsConstructor
public class AppController {
    @Autowired
    private UserRepository userRepo;
    private RegistrationService registrationService;

    @PostMapping("/register")
    public String register(@RequestBody
                           RegistrationRequest request){
        return registrationService.register(request);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }


    @GetMapping("")
    public String viewHomePage() {

        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
        return "register_success";

    }





}
