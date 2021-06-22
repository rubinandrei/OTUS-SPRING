package com.questionnare;

import com.questionnare.dao.QuizDaoImpl;
import com.questionnare.localization.LocalizationConfiguration;
import com.questionnare.localization.LocalizationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import com.questionnare.dao.QuizDaoImpl;

import com.questionnare.services.IOServiceImpl;
import com.questionnare.services.IOStreamFactory;
import com.questionnare.services.QuizServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class InOutServiceTest {

    private static final String USER_NAME = "Andrei Andreev";



    @Test
    public void readAnswerTest() throws IOException {
        System.setIn(new ByteArrayInputStream(USER_NAME.getBytes()));
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("local/messages");
        MessageSource mss = messageSource;
        QuizDaoImpl dao = new QuizDaoImpl(new LocalizationConfiguration(mss,"en"),"test_questions_%s.csv");
        dao.getUser();
        LocalizationServiceImpl loc = new LocalizationServiceImpl(new LocalizationConfiguration(mss,"en"));
        IOServiceImpl ioService = new IOServiceImpl(new IOStreamFactory(),loc);
        QuizServiceImpl quizService = new QuizServiceImpl(dao,loc);
        quizService.setIOServiceImpl(ioService);
        quizService.setUser(" ");
        assertThat(dao.getUser().getFirstName(), is(USER_NAME.split(" ")[0]));
        assertThat(dao.getUser().getLastName(), is(USER_NAME.split(" ")[1]));
    }


}
