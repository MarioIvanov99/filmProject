package com.example.filmProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "interested_in", columnDefinition = "int default 0")
    private Integer interestedIn;

    @Column(name = "watched", columnDefinition = "int default 0")
    private Integer watched;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserMovieRating> userMovieRatings;

    public Set<UserMovieRating> getUserMovieRatings() {
        return userMovieRatings;
    }

    public void setUserMovieRatings(Set<UserMovieRating> userMovieRatings) {
        this.userMovieRatings = userMovieRatings;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getInterestedIn() {
        return interestedIn;
    }

    public Integer getWatched() {
        return watched;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setInterestedIn(Integer interestedIn) {
        this.interestedIn = interestedIn;
    }

    public void setWatched(Integer watched) {
        this.watched = watched;
    }
}
