package com.questionnare.dao;

import com.questionnare.dto.Question;
import com.questionnare.dto.User;

import java.util.List;

public interface UserDao {


    User getUser();

    void setUser(String firstName, String secondName);

    void setQuestions(List<Question> questions);

    List<Question> getUserQuestions();



}
