package com.nhnacademy.edu.springframework.project.repository;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public interface Scores {
    void load() throws IOException;

    List<Score> findAll() throws IOException;
}
