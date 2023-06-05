package com.example.filmProject.Controller;

import com.example.filmProject.model.Employee;
import com.example.filmProject.model.User;
import com.example.filmProject.repository.EmployeeRepository;
import com.example.filmProject.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, Model model) {

        // Check if the user exists in the users table
        User user = userRepository.getUserByUsernameAndPassword(username, password);

        if (user != null) {

            // Set logged in user for the current session
            session.setAttribute("loggedInUser", user);
            return "redirect:/home";

        }

        // Check if the user exists in the employees table
        Employee employee = employeeRepository.getEmployeeByUsernameAndPassword(username, password);

        if (employee != null) {

            // Set logged in employee for the current session
            session.setAttribute("loggedInEmployee", employee);
            return "redirect:/home";

        }

        // If no match found, display an error message
        model.addAttribute("error", "Invalid username or password");
        return "login";

    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {

        // Reset session
        session.invalidate();
        return "redirect:/home";

    }
}