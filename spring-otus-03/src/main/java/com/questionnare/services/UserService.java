package com.questionnare.services;

import com.questionnare.dto.Question;

import java.util.List;


public interface UserService {


    void setQuizUser(String infoMessage);
    void calcUserResult();
    String getUserName();
    List<String> showUserResults();
    void setUserQuestions(List<Question> questions);

}
