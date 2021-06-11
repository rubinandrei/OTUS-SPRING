package questionnaire.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import questionnaire.dto.Answer;
import questionnaire.dto.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import questionnaire.dto.User;


@Repository
public class QuizDaoImpl implements QuizDao {

    final static Logger LOGGER = Logger.getLogger(QuizDaoImpl.class);
    private final List<Question> questions = new ArrayList<>();
    private final List<Answer> answers = new ArrayList<>();
    private User user;
    private final String question_file;


    public QuizDaoImpl(@Value("${questions.file}") String questionFile) {
        this.question_file = questionFile;
    }



    public void readFile() throws IOException, NullPointerException {
        try (InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(question_file);
             BufferedReader questionReader = new BufferedReader(new InputStreamReader(stream))) {
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
