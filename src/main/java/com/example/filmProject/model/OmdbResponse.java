package com.example.filmProject.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OmdbResponse { // Used to interact with the relevant attributes from the OMDB API
    private String imdbRating;
    private String plot;
    private String poster;

    public String getImdbRating() {
        return imdbRating;
    }

    public String getPlot() {
        return plot;
    }

    public String getPoster() {
        return poster;
    }

    @JsonProperty("imdbRating")
    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    @JsonProperty("Plot")
    public void setPlot(String plot) {
        this.plot = plot;
    }

    @JsonProperty("Poster")
    public void setPoster(String poster) {
        this.poster = poster;
    }
}