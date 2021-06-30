package com.questionnare.services;

import com.questionnare.dao.UserDao;
import com.questionnare.dao.UserDaoImpl;
import com.questionnare.dto.Question;
import com.questionnare.dto.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Value("${max.passValue}")
    private int passValue;
    private final UserDao userDao;
    private final IOService inOut;

    public UserServiceImpl(UserDaoImpl userDao, IOServiceImpl inOut) {
        this.userDao = userDao;
        this.inOut = inOut;
    }

    @Override
    public void setQuizUser(String infoMessage) {
        while (true) {
            String consoleUser = inOut.readConsoleString(infoMessage);
            if (validateUser(consoleUser)) {
                String[] userNameSurname = consoleUser.split(" ");
                userDao.setUser(userNameSurname[0], userNameSurname[1]);
                break;
            }
        }
    }

    public boolean validateUser(String user) {
        return user.split(" ").length > 1;
    }


    @Override
    public void calcUserResult() {
        int count = (int) userDao.getUserQuestions()
                .stream()
                .filter(question -> question.getAnswers().get(question.getAnswerID() - 1).isCorrect())
                .count();
        userDao.getUser().setCountCorrectAnswers(count);
    }

    @Override
    public String getUserName() {
        User user = userDao.getUser();
        return user.getFirstName() + " " + user.getLastName();

    }

    @Override
    public List<String> showUserResults() {
        List<String> resultFormat = userDao.getUser().getCountCorrectAnswers() > 0 ?
                Arrays.asList(
                        userDao.getUser().getFirstName(),
                        userDao.getUser().getLastName(),
                        String.valueOf(userDao.getUser().getCountCorrectAnswers()),
                        String.valueOf(userDao.getUser().getCountCorrectAnswers() * 100 / userDao.getUserQuestions().size()))
                : Arrays.asList(
                userDao.getUser().getFirstName(),
                userDao.getUser().getLastName(),
                "0", "0");

        return resultFormat;
    }

    @Override
    public void setUserQuestions(List<Question> questions){
        userDao.setQuestions(questions);
    }

}
