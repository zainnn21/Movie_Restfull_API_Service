package com.example.movie.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "movie")
public class Movie implements Serializable {

    @Id
    private Integer id;
    private String title;
    private String description;
    private Float rating;
    private String image;
    private LocalDateTime created_at;
    private LocalDateTime update_at;
}
