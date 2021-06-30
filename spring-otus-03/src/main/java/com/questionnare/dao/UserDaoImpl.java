package com.questionnare.dao;

import com.questionnare.dto.Question;
import com.questionnare.dto.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoImpl implements UserDao{

    private User user;


    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(String firstName, String secondName) {
             user = new User(firstName,secondName);
    }

    @Override
    public void setQuestions(List<Question> questions) {
        user.setQuestions(questions);
    }

    @Override
    public List<Question> getUserQuestions() {
        return user.getQuestions();
    }

}
