package com.example.filmProject.Controller;

import com.example.filmProject.model.Employee;
import com.example.filmProject.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @GetMapping("home")
    public String homePage(Model model, HttpSession session) {

        // Retrieve the logged-in user from the session
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        Employee loggedInEmployee = (Employee) session.getAttribute("loggedInEmployee");

        if (loggedInEmployee != null) {
            // Pass the logged-in employee object to the view
            model.addAttribute("loggedInEmployee", loggedInEmployee);
        }
        if (loggedInUser != null) {
            // Pass the logged-in user object to the view
            model.addAttribute("loggedInUser", loggedInUser);
        }

        return "home";

    }

}
