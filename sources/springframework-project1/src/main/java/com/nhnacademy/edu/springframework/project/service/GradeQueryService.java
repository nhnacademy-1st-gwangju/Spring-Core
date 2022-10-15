package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.repository.Score;

import java.io.IOException;
import java.util.List;

public interface GradeQueryService {
    List<Score> getScoreByStudentName(String name) throws IOException;
    Score getScoreByStudentSeq(int seq) throws IOException;
}
