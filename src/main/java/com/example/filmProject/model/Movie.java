package com.example.filmProject.model;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @Column(name = "mid", nullable = false)
    private String mid;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "release_year", nullable = false)
    private Integer releaseYear;

    @Column(name = "runtime_minutes", nullable = false)
    private Integer runtimeMinutes;

    @Column(name = "genres", nullable = false)
    private String genres;

    @OneToMany(mappedBy = "movie")
    private Set<UserMovieRating> userMovieRatings;

    public Set<UserMovieRating> getUserMovieRatings() {
        return userMovieRatings;
    }

    public void setUserMovieRatings(Set<UserMovieRating> userMovieRatings) {
        this.userMovieRatings = userMovieRatings;
    }

    public String getMid() {
        return mid;
    }

    public String getTitle() {
        return title;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public Integer getRuntimeMinutes() {
        return runtimeMinutes;
    }

    public String getGenres() {
        return genres;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setRuntimeMinutes(Integer runtimeMinutes) {
        this.runtimeMinutes = runtimeMinutes;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }
}
