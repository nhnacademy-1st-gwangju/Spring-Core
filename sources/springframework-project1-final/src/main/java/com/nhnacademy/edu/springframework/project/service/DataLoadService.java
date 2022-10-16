package com.nhnacademy.edu.springframework.project.service;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public interface DataLoadService {
    void loadAndMerge() throws IOException;
}
