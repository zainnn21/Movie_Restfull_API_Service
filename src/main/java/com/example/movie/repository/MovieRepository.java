package com.example.movie.repository;

import com.example.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository <Movie, Integer> {
    List<Movie>findByTitle(String title);
}
