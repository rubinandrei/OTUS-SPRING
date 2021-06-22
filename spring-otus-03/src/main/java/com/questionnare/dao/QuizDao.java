package com.questionnare.dao;

import com.questionnare.dto.Answer;
import com.questionnare.dto.Question;
import com.questionnare.dto.User;

import java.io.IOException;
import java.util.List;

public interface QuizDao {

    List<Question> getQuestions();

    List<Answer> getAnswers();

    User getUser();

    void setUser(String firstName, String secondName);

    void readFile() throws IOException, NullPointerException;
}
