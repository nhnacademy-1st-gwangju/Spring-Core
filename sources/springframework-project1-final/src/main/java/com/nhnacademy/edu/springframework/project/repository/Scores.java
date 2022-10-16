package com.nhnacademy.edu.springframework.project.repository;

import java.io.IOException;
import java.util.List;

public interface Scores {
    void load() throws IOException;

    List<Score> findAll() throws IOException;
}
