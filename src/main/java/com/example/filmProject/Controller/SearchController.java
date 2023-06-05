package com.example.filmProject.Controller;

import com.example.filmProject.model.*;
import com.example.filmProject.repository.MovieRepository;
import com.example.filmProject.repository.UserMovieRatingRepository;
import com.example.filmProject.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private final MovieRepository movieRepository;

    @Autowired
    private UserMovieRatingRepository userMovieRatingRepository;

    @Autowired
    private UserRepository userRepository;

    public SearchController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("/search")
    public String searchForm(Model model) {

        // Add search attributes to search page
        model.addAttribute("title", "");
        model.addAttribute("minReleaseYear", null);
        model.addAttribute("maxReleaseYear", null);
        model.addAttribute("minRuntimeMinutes", null);
        model.addAttribute("maxRuntimeMinutes", null);
        model.addAttribute("genres", "");
        model.addAttribute("movies", Collections.emptyList());

        return "search";
    }

    @PostMapping("/search")
    public String searchMovies(
            @RequestParam(name = "title", required = false, defaultValue = "") String title,
            @RequestParam(name = "minReleaseYear", required = false) Integer minReleaseYear,
            @RequestParam(name = "maxReleaseYear", required = false) Integer maxReleaseYear,
            @RequestParam(name = "minRuntimeMinutes", required = false) Integer minRuntimeMinutes,
            @RequestParam(name = "maxRuntimeMinutes", required = false) Integer maxRuntimeMinutes,
            @RequestParam(name = "genres", required = false, defaultValue = "") String genres,
            Model model,
            HttpSession session) {

        // Get all movies from search
        List<Movie> movies = movieRepository.searchMovies(title, minReleaseYear, maxReleaseYear,
                minRuntimeMinutes, maxRuntimeMinutes, genres);

        // Retrieve the currently logged-in user from the session
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        // Set search data and reload the page
        model.addAttribute("title", title);
        model.addAttribute("minReleaseYear", minReleaseYear);
        model.addAttribute("maxReleaseYear", maxReleaseYear);
        model.addAttribute("minRuntimeMinutes", minRuntimeMinutes);
        model.addAttribute("maxRuntimeMinutes", maxRuntimeMinutes);
        model.addAttribute("genres", genres);
        model.addAttribute("movies", movies);
        model.addAttribute("loggedInUser", loggedInUser);

        return "search";
    }

    @GetMapping("/movie/{id}")
    public String getMovieDetails(@PathVariable("id") String movieId, Model model) {

        // Make a request to the OMDB API to fetch movie details
        RestTemplate restTemplate = new RestTemplate();
        String omdbUrl = "http://www.omdbapi.com/?apikey=9bf08e6d&i=" + movieId +"&plot=full"; // returns complete summary
        ResponseEntity<OmdbResponse> response = restTemplate.getForEntity(omdbUrl, OmdbResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            OmdbResponse omdbResponse = response.getBody();
            if (omdbResponse != null) {

                // Extract relevant information from the OMDB response
                String imdbRating = omdbResponse.getImdbRating();
                String plot = omdbResponse.getPlot();
                String poster = omdbResponse.getPoster();

                // Pass the movie details to the view
                model.addAttribute("imdbRating", imdbRating);
                model.addAttribute("plot", plot);
                model.addAttribute("poster", poster);

            }
        }

        return "details";
    }

    @PostMapping("/addMovie")
    public String addMovieToUserList(@RequestParam(name = "mid") String mid, HttpSession session) {

        // Retrieve the currently logged-in user from the session
        User user = (User) session.getAttribute("loggedInUser");

        // Find the movie object based on the given movie ID
        Movie movie = movieRepository.findById(mid).orElse(null);

        // Check if the movie already exists in the user's movie list
        if (user.getUserMovieRatings().stream().anyMatch(rating -> rating.getMovie().equals(movie))) {
            throw new RuntimeException("Movie already added to watch list");
        }

        // Get a new user to avoid merge errors
        User newUser = userRepository.getUserById(user.getId());

        // Create a new UserMovieRatingId instance
        UserMovieRatingId userMovieRatingId = new UserMovieRatingId();
        userMovieRatingId.setUserId(newUser.getId()); // Set the User entity
        userMovieRatingId.setMovieId(movie.getMid()); // Set the Movie entity

        // Create a new UserMovieRating instance
        UserMovieRating userMovieRating = new UserMovieRating();
        userMovieRating.setId(userMovieRatingId);
        userMovieRating.setUser(newUser);
        userMovieRating.setMovie(movie);
        userMovieRating.setHasSeen(false);
        userMovieRating.setRating(0);

        // Save the UserMovieRating object to the database
        userMovieRatingRepository.save(userMovieRating);

        // Update the user's movie list
        user.getUserMovieRatings().add(userMovieRating);

        // Increment the interested_in value of the user
        user.setInterestedIn(user.getInterestedIn() + 1);

        // Save the changes to the user entity
        userRepository.updateUser(newUser);

        return "redirect:/search";
    }
}
