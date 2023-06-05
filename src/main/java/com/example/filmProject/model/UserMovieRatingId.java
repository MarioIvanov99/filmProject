package com.example.filmProject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserMovieRatingId {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "movie_id")
    private String movieId;

    public int getUserId() {
        return userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
