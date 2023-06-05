package com.example.filmProject.repository;

import com.example.filmProject.model.UserMovieRating;
import com.example.filmProject.model.UserMovieRatingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMovieRatingRepository extends JpaRepository<UserMovieRating, UserMovieRatingId> {
}
