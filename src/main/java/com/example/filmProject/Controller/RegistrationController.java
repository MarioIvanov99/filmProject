package com.example.filmProject.Controller;

import com.example.filmProject.model.User;
import com.example.filmProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registrationPage() {
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password) {

        // Create new User object and set relevant data
        User user = new User();
        user.setEmail(email);
        user.setName(username);
        user.setPassword(password);
        user.setInterestedIn(0);
        user.setWatched(0);

        // Update database
        userRepository.addUser(user);

        return "redirect:/login"; // Redirect to login page after registration
    }
}