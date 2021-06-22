package com.questionnare;

import com.questionnare.dao.QuizDaoImpl;
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
    QuizDaoImpl dao;


    @Test
    public void enDaoTest() throws IOException {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("local/messages");
        QuizDaoImpl dao = new QuizDaoImpl(new LocalizationConfiguration(messageSource,"en"),"test_questions_%s.csv");
        dao.readFile();
        assertThat(dao.getQuestions(), hasSize(1));
        assertThat(dao.getAnswers(), hasSize(1));
    }

    @Test
    public void ruDaoTest() throws IOException {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("local/messages");
        QuizDaoImpl dao = new QuizDaoImpl(new LocalizationConfiguration(messageSource,"ru"),"test_questions_%s.csv");
        dao.readFile();
        assertThat(dao.getQuestions(), hasSize(2));
        assertThat(dao.getAnswers(), hasSize(7));
    }

    @Test
    public void userDaoTest() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("local/messages");
        QuizDaoImpl dao = new QuizDaoImpl(new LocalizationConfiguration(messageSource,"en"),"test_questions_%s.csv");
        dao.setUser("Andrei", "Rubin");
        dao.getUser().setCountCorrectAnswers(5);
        assertThat(dao.getUser().getFirstName(), is("Andrei"));
        assertThat(dao.getUser().getLastName(), is("Rubin"));
        assertThat(dao.getUser().getCountCorrectAnswers(), is(5));
    }

}
