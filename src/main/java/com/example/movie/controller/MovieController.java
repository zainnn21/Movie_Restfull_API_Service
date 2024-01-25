package com.example.movie.controller;

import com.example.movie.entity.Movie;
import com.example.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    private void ApiError() {
        LocalDateTime timestamp = LocalDateTime.now();
    }

    @GetMapping("/Movies")
    public ResponseEntity <List<Movie>> findAll(
            @RequestParam(name = "title",
            required = false,
            defaultValue = "") String title) {
        try {
            List<Movie> movies;
            if (StringUtils.hasText(title)){
                movies = movieRepository.findByTitle(title);
            } else {
                movies = movieRepository.findAll();
            }
            if(movies.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(movies,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/Movies/{id}")
    public ResponseEntity<Movie>findById(
            @PathVariable Integer id) {
        Optional<Movie> movieData = movieRepository.findById(id);
        System.out.println(id);
        if ( movieData.isPresent()){
            return new ResponseEntity<>(movieData.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/Movies")
    public ResponseEntity<Movie> create(
            @RequestBody Movie movie){
        try {
            Movie newMovie = new Movie();
            newMovie.setId(movie.getId());
            newMovie.setTitle(movie.getTitle());
            newMovie.setDescription(movie.getDescription());
            newMovie.setRating(movie.getRating());
            newMovie.setImage(movie.getImage());
            newMovie.setCreated_at(movie.getCreated_at());
            newMovie.setUpdate_at(movie.getUpdate_at());
            return new ResponseEntity<>(movieRepository.save(newMovie), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/Movies/{id}")
    public ResponseEntity<Movie> update(
            @PathVariable Integer id,
            @RequestBody Movie movie) {
        Optional<Movie> movieData = movieRepository.findById(id);
        if(movieData.isPresent()){
            System.out.println(id);
            Movie updateMovie = movieData.get();
            updateMovie.setTitle(movie.getTitle());
            updateMovie.setDescription(movie.getDescription());
            updateMovie.setRating(movie.getRating());
            updateMovie.setImage(movie.getImage());
            updateMovie.setCreated_at(movie.getCreated_at());
            updateMovie.setUpdate_at(movie.getUpdate_at());
            return new ResponseEntity<>(movieRepository.save(updateMovie),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/Movies/{id}")
    public ResponseEntity<HttpStatus> delete(
            @PathVariable Integer id){
        try {
            movieRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
