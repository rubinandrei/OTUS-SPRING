package com.questionnare.services;

import com.questionnare.dto.Question;
import com.questionnare.dto.User;

import java.io.IOException;
import java.util.List;

public interface QuizService {

    void initQuiz()throws IOException;
    void showQuiz();
    List<Question> getQuestion();
}
