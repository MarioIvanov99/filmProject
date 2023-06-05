package com.example.filmProject.Controller;

import com.example.filmProject.model.*;
import com.example.filmProject.repository.EmployeeRepository;
import com.example.filmProject.repository.MovieRepository;
import com.example.filmProject.repository.UserMovieRatingRepository;
import com.example.filmProject.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ProfileController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMovieRatingRepository userMovieRatingRepository;

    @GetMapping("/profile")
    public String profilePage(Model model, HttpSession session) {

        // Retrieve the logged-in user from the session
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        Employee loggedInEmployee = (Employee) session.getAttribute("loggedInEmployee");

        if (loggedInEmployee != null) {
            // Pass the logged-in employee object to the view
            List<Employee> employees = employeeRepository.getAllEmployees();
            List<User> users = userRepository.getAllUsers();

            // Adds relevant information for admin operations
            model.addAttribute("loggedInEmployee", loggedInEmployee);
            model.addAttribute("employees", employees);
            model.addAttribute("users", users);

        }

        if (loggedInUser != null) {

            // Pass the logged-in user object to the view
            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("userMovieRatings", loggedInUser.getUserMovieRatings());

        }

        return "myprofile";
    }

    @PostMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam("id") Integer id) {

        // Delete employee
        employeeRepository.deleteEmployee(id);

        return "redirect:/profile";

    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") Integer id) {

        // Delete user
        userRepository.deleteUser(id);

        return "redirect:/profile";

    }

    @PostMapping("/addEmployee")
    public String addEmployee(@RequestParam("employeeName") String employeeName, @RequestParam("employeeEmail") String employeeEmail) {

        // Add new employee. Only admins have passwords
        Employee employee = new Employee();
        employee.setName(employeeName);
        employee.setEmail(employeeEmail);
        employeeRepository.addEmployee(employee);

        return "redirect:/profile";
    }

    @PostMapping("/updateWatched")
    public String updateWatched(@RequestParam("ratingId") String movieId, HttpSession session) {

        // Retrieve the logged-in user from the session
        // Retrieve userMovieRatingId
        UserMovieRatingId userMovieRatingId = new UserMovieRatingId();
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        // Set relevent information for rating object
        userMovieRatingId.setMovieId(movieId);
        userMovieRatingId.setUserId(loggedInUser.getId());

        // Get an optional object from the default JPA function
        Optional<UserMovieRating> optionalRating = userMovieRatingRepository.findById(userMovieRatingId);

        // If user exists, update rating data
        if (optionalRating.isPresent()) {
            UserMovieRating rating = optionalRating.get();
            rating.setHasSeen(true);

            userMovieRatingRepository.save(rating);

            // Update the correct value in the user's list
            for (UserMovieRating r : loggedInUser.getUserMovieRatings()) {
                if (r.getMovie().getMid().equals(movieId)) {
                    r.setHasSeen(true);
                    break;
                }
            }

            // Update user data
            loggedInUser.setWatched(loggedInUser.getWatched()+1);
            loggedInUser.setInterestedIn(loggedInUser.getInterestedIn()-1);
            userRepository.updateUser(loggedInUser);
        }


        return "redirect:/profile";
    }

    @PostMapping("/updateRating")
    public String updateWatched(@RequestParam("ratingId") String movieId,@RequestParam("rating") Integer num, HttpSession session) {

        // Retrieve the logged-in user from the session
        // Retrieve userMovieRatingId
        UserMovieRatingId userMovieRatingId = new UserMovieRatingId();
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        // Set relevent information for rating object
        userMovieRatingId.setMovieId(movieId);
        userMovieRatingId.setUserId(loggedInUser.getId());

        // Get an optional object from the default JPA function
        Optional<UserMovieRating> optionalRating = userMovieRatingRepository.findById(userMovieRatingId);

        // If user exists, update rating data
        if (optionalRating.isPresent()) {
            UserMovieRating rating = optionalRating.get();
            rating.setRating(num);

            userMovieRatingRepository.save(rating);

            // Update the correct value in the user's list
            for (UserMovieRating r : loggedInUser.getUserMovieRatings()) {
                if (r.getMovie().getMid().equals(movieId)) {
                    r.setRating(num);
                    break;
                }
            }

            // Update user data
            userRepository.updateUser(loggedInUser);
        }


        return "redirect:/profile";
    }

}