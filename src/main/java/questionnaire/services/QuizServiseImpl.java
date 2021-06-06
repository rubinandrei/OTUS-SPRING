package questionnaire.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import questionnaire.dao.CSVDao;
import questionnaire.dao.DaoFactory;
import questionnaire.dao.UserDao;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QuizServiseImpl implements QuizService {

    private static final String QUIZ_OUT = "\n %s \n";
    private static final String ANSWER_OUT = "\t %d.  -  %s \n\n";

    private final CSVDao csvDao;
    private final UserDao userDao;
    private final IOServiceImpl inOut;


    @Autowired
    public QuizServiseImpl(DaoFactory daoFactory, IOServiceImpl inOut) {
        this.csvDao = daoFactory.getCsvDao();
        this.userDao = daoFactory.getUserDao();
        this.inOut = inOut;
    }

    @Override
    public void showQustions() {
        inOut.printString("Start quiz:");
        AtomicInteger answerNb = new AtomicInteger();
        csvDao.getQuestions().stream().forEach(question -> {
            answerNb.set(0);
            inOut.printString(String.format(QUIZ_OUT, question.getQuestion()));
            question.getAnswers().stream().forEach(answer -> {
                inOut.printString(String.format(ANSWER_OUT, answerNb.incrementAndGet(), answer.getAnswer()));
            });
            inOut.readQuestionAnswer(answerNb.get());
        });
    }

    @Override
    public void setUser() {
        inOut.printString("pleas write your name");
        String firstName = inOut.readString();
        inOut.printString("pleas write your last name");
        String lastName = inOut.readString();
        userDao.setUser(firstName,lastName);
    }
}
