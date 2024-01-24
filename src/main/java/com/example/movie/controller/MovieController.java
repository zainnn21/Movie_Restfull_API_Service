package com.example.movie.controller;

import com.example.movie.entity.Movie;
import com.example.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/Movie")
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
            @PathVariable("id") String id) {
        Optional<Movie> movieData = movieRepository.findById(id);

        return movieData.map(movie -> new ResponseEntity<>(movie, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/Movie")
    public ResponseEntity<Movie> create(
            @RequestBody Movie movie){
        try {
            Movie newMovie = new Movie();
            newMovie.setId(1L);
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

    @PutMapping("/Movie/id")
    public ResponseEntity<Movie> update(
            @PathVariable("id")Long id,
            @RequestBody Movie movie) {
        Optional<Movie> movieData = movieRepository.findById(String.valueOf(id));
        if(movieData.isPresent()){
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

    @DeleteMapping("/movie/{id}")
    public ResponseEntity<HttpStatus> delete(
            @PathVariable("id") String id){
        try {
            movieRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
