package com.questionnare.dao;

import com.questionnare.localization.LocalizationConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import com.questionnare.dto.Answer;
import com.questionnare.dto.Question;
import com.questionnare.dto.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


@Repository
public class QuizDaoImpl implements QuizDao {
    private final List<Question> questions = new ArrayList<>();
    private final List<Answer> answers = new ArrayList<>();
    private User user;
    private String question_file;



    @Autowired
    public QuizDaoImpl(LocalizationConfiguration local, @Value("${quiz.questionnaire.file}") String questionFile) {
          this.question_file = String.format(questionFile, local.getCurrentLanguage());
    }



    public void readFile() throws IOException, NullPointerException {
        try (InputStream stream = new ClassPathResource(question_file).getInputStream();
             BufferedReader questionReader = new BufferedReader(new InputStreamReader(stream))){;
            questionReader.lines()
                    .skip(1)
                    .map(q -> q.split(";"))
                    .forEach(q -> {
                        if (Integer.parseInt(q[1]) == 1) {
                            answers.add(new Answer(Integer.parseInt(q[0]), Integer.parseInt(q[3]), q[2], Boolean.parseBoolean(q[4])));
                        } else {
                            questions.add(new Question(Integer.parseInt(q[0]), q[2]));
                        }
                    });
        }
    }

    @Override
    public List<Question> getQuestions() {
        return this.questions;
    }

    @Override
    public List<Answer> getAnswers() {
        return this.answers;
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public void setUser(String firstName, String secondName) {
         user = new User(firstName,secondName);
    }


}
