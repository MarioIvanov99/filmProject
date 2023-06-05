package com.example.filmProject.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_movie_ratings")
public class UserMovieRating {

    @EmbeddedId
    private UserMovieRatingId id;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id", insertable=false, updatable=false)
    private Movie movie;

    @Column(name = "has_seen")
    private boolean hasSeen;

    @Column(name = "rating")
    private int rating;

    public User getUser() {
        return user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public UserMovieRatingId getId() {
        return id;
    }

    public boolean isHasSeen() {
        return hasSeen;
    }

    public int getRating() {
        return rating;
    }

    public void setId(UserMovieRatingId id) {
        this.id = id;
    }

    public void setHasSeen(boolean hasSeen) {
        this.hasSeen = hasSeen;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
