package com.example.filmProject.repository;

import com.example.filmProject.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
    // Gets all movies that satisfy a given condition
    @Query("SELECT m FROM Movie m " +
            "WHERE (:title IS NULL OR m.title LIKE %:title%) " +
            "AND ((:minReleaseYear IS NULL OR m.releaseYear >= :minReleaseYear) " +
            "AND (:maxReleaseYear IS NULL OR m.releaseYear <= :maxReleaseYear)) " +
            "AND ((:minRuntimeMinutes IS NULL OR m.runtimeMinutes >= :minRuntimeMinutes) " +
            "AND (:maxRuntimeMinutes IS NULL OR m.runtimeMinutes <= :maxRuntimeMinutes)) " +
            "AND (:genres IS NULL OR m.genres LIKE %:genres%)")
    List<Movie> searchMovies(
            String title, Integer minReleaseYear, Integer maxReleaseYear, Integer minRuntimeMinutes, Integer maxRuntimeMinutes, String genres);
}
