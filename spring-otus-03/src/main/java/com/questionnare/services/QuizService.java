package com.questionnare.services;

import java.io.IOException;
import java.util.List;

public interface QuizService {

    void initQuiz()throws IOException;
    void setUser(String message);
    void showQuiz();
    void calcResult();
    List<String> showResult();
    String getUser();
}
