package com.questionnare.services;

import com.questionnare.localization.LocalizationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.questionnare.dao.QuizDao;
import com.questionnare.dto.Question;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    @Value("${max.passValue}")
    private int passValue;



    private static final String QUIZ_OUT = "\n %s \n";
    private static final String ANSWER_OUT = "\t %d.  -  %s \n\n";


    private List<Question> questions;
    private final QuizDao dao;
    private IOServiceImpl inOut;


    @Autowired
    public QuizServiceImpl(QuizDao dao) {
        this.dao = dao;
    }

    @Autowired
    public void setIOServiceImpl(IOServiceImpl inOut){
        this.inOut = inOut;
    }

    @Override
    public void initQuiz() throws IOException {
        this.dao.readFile();
        questions = dao.getQuestions().stream()
                .map(this::apply)
                .collect(Collectors.toList());
    }

    @Override
    public void showQuiz(){
        AtomicInteger answerNb = new AtomicInteger();
        questions.forEach(question -> {
            answerNb.set(0);
            inOut.printString(String.format(QUIZ_OUT, question.getQuestion()));
            question.getAnswers().forEach(answer ->
                    inOut.printString(String.format(ANSWER_OUT, answerNb.incrementAndGet(), answer.getAnswer())));
            question.setAnswerID(inOut.readQuestionAnswer(answerNb.get()));
        });
    }

    public void setUser(String message) {
        String[] user = inOut.readUser(message);
        dao.setUser(user[0], user[1]);
    }

    @Override
    public void calcResult() {
        int count = (int) questions.stream()
                .filter(question -> question.getAnswers().get(question.getAnswerID() - 1).isCorrect())
                .count();
        dao.getUser().setCountCorrectAnswers(count);
    }

    @Override
    public String getUser() {
        return dao.getUser().getFirstName() +"   " + dao.getUser().getLastName();
    }


    @Override
    public List<String> showResult() {
        List<String> resultFormat = dao.getUser().getCountCorrectAnswers() > 0?
                Arrays.asList(
                dao.getUser().getFirstName(),
                dao.getUser().getLastName(),
                String.valueOf(dao.getUser().getCountCorrectAnswers()),
                String.valueOf(dao.getUser().getCountCorrectAnswers() * 100 / questions.size()))
                :Arrays.asList(
                dao.getUser().getFirstName(),
                dao.getUser().getLastName(),
                "0", "0");

        return resultFormat;

    }

    private Question apply(Question x) {
        Question question = x.clone();
        question.setAnswers(dao.getAnswers());
        return question;
    }
}
