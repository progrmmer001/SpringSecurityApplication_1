package org.example.controller;

import jakarta.validation.Valid;
import org.example.model.AuthUser;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String logIn(Model model, @RequestParam(required = false) String error) {
        model.addAttribute("errormessage", error);
        return "auth";
    }

    @GetMapping("/sign")
    public String sign() {
        return "SignIn";
    }

    @PostMapping("/signIn")
    public String signIn(@ModelAttribute AuthUser authUser) {
        authUser.setRole("USER");
        AuthUser authUser1 = AuthUser.builder()
                .username(authUser.getUsername())
                .password(passwordEncoder.encode(authUser.getPassword()))
                .role("USER")
                .build();
        userRepository.save(authUser1);
        return "redirect:/auth/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }
}
