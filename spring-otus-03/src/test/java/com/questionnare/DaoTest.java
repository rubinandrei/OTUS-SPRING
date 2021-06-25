package com.questionnare;

import com.questionnare.dao.QuizDaoImpl;
import com.questionnare.dao.UserDao;
import com.questionnare.dao.UserDaoImpl;
import com.questionnare.localization.LocalizationConfiguration;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.io.IOException;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class DaoTest {

    ResourceBundleMessageSource messageSource;
    QuizDaoImpl dao;

    @Before
    public void init(){
        messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("test/test");
    }

   @Test
    public void enQuizDaoTest(){
        dao = new QuizDaoImpl(new LocalizationConfiguration(messageSource,"en"),"test_questions_%s.csv");
        dao.readFile();
        assertThat(dao.getQuestions(), hasSize(1));
        assertThat(dao.getAnswers(), hasSize(1));
    }

    @Test
    public void ruQuizDaoTest(){
        dao = new QuizDaoImpl(new LocalizationConfiguration(messageSource,"ru"),"test_questions_%s.csv");
        dao.readFile();
        assertThat(dao.getQuestions(), hasSize(2));
        assertThat(dao.getAnswers(), hasSize(7));
    }

    @Test
    public void userDaoTest() {
        QuizDaoImpl dao = new QuizDaoImpl(new LocalizationConfiguration(messageSource,"en"),"test_questions_%s.csv");
        dao.readFile();

        UserDao userDao = new UserDaoImpl();
        userDao.setUser("Andrei", "Rubin");
        assertThat(userDao.getUser().getFirstName(), is("Andrei"));
        assertThat(userDao.getUser().getLastName(), is("Rubin"));

        userDao.setQuestions(dao.getQuestions());
        assertThat(userDao.getUser().getQuestions().size(), is(1));

        userDao.getUser().setCountCorrectAnswers(5);
        assertThat(userDao.getUser().getCountCorrectAnswers(), is(5));

    }

}
