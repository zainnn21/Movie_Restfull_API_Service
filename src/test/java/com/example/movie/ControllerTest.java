package com.example.movie;

import com.example.movie.controller.MovieController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ControllerTest {
    @Autowired
    private MovieController controller;
    @Test
    void contextLoads() throws Exception{
        assertThat(controller).isNotNull();
    }
}
