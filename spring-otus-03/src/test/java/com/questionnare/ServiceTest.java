package com.questionnare;

import com.questionnare.dao.QuizDao;
import com.questionnare.dao.QuizDaoImpl;
import com.questionnare.dao.UserDao;
import com.questionnare.dao.UserDaoImpl;
import com.questionnare.dto.Answer;
import com.questionnare.dto.Question;
import com.questionnare.localization.LocalizationConfiguration;
import com.questionnare.localization.LocalizationServiceImpl;
import com.questionnare.services.IOService;
import com.questionnare.services.QuizService;
import com.questionnare.services.UserService;
import com.questionnare.services.UserServiceImpl;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class ServiceTest {


    private static final String USER_NAME = "Andrei Andreev";
    private UserService userService;
    private IOServiceImpl ioService;
    private LocalizationServiceImpl loc;
    private ResourceBundleMessageSource messageSource;
    private MessageSource mss;
    private QuizDao dao;
    private QuizServiceImpl quizService;
    private final List<String> RESULT = List.of("Andrei", "Andreev", "1", "100");

    @Before
    public void init() {
        System.setIn(new ByteArrayInputStream(USER_NAME.getBytes()));
        messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("test/test");
        mss = messageSource;
        loc = new LocalizationServiceImpl(new LocalizationConfiguration(mss, "en"));
        ioService = new IOServiceImpl(new IOStreamFactory(), loc);
        userService = new UserServiceImpl(new UserDaoImpl(), ioService);
        dao = new QuizDaoImpl(new LocalizationConfiguration(mss, "en"), "test_questions_%s.csv");
        quizService = new QuizServiceImpl(dao);
    }

    @Test
    public void userServiceTest() {
        userService.setQuizUser(" ");
        assertThat(userService.getUserName(), is(USER_NAME));
        List<Question> questionList = new ArrayList<>();
        List<Answer> answerList = new ArrayList<>();
        answerList.add(new Answer(1, 1, "Test Answer", true));
        Question question = new Question(1,"Test question");
        question.setAnswerID(1);
        question.setAnswers(answerList);
        questionList.add(question);
        userService.setUserQuestions(questionList);
        userService.calcUserResult();
        assertThat(userService.showUserResults(), is(RESULT));
    }

    @Test
    public void setQuizServiceTest() {
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        ioService = new IOServiceImpl(new IOStreamFactory(), loc);
        quizService.setIOServiceImpl(ioService);
        quizService.initQuiz();
        assertThat(quizService.getQuestion().get(0).getAnswerID(), is(0));
        assertThat(quizService.getQuestion().get(0).getAnswers().size(),is(1));
        quizService.showQuiz();
        assertThat(quizService.getQuestion().get(0).getAnswerID(), is(1));
    }

    @Test
    public void setIoServiceTest() {
        System.setIn(new ByteArrayInputStream("Test".getBytes()));
        ioService = new IOServiceImpl(new IOStreamFactory(), loc);
        assertThat(ioService.readConsoleString(""), is("Test"));
    }

    @Test
    public void localisation(){
        messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("localTest/messages");
        mss = messageSource;
        loc = new LocalizationServiceImpl(new LocalizationConfiguration(mss, "en"));
        assertThat(loc.localize("input.name",""),is("Input name and sure name:"));
        assertThat(loc.localize("greeting",USER_NAME),is("Hello Andrei Andreev "));
        assertThat(loc.localize("score","1","2","3","4"),is("1 2 Your result 3 the correct question is 4%"));
    }


}
