package questionnaire.dao;

import questionnaire.dto.Answer;
import questionnaire.dto.Question;
import questionnaire.dto.User;

import java.io.IOException;
import java.util.List;

public interface QuizDao {

    List<Question> getQuestions();

    List<Answer> getAnswers();

    User getUser();

    void setUser(String firstName, String secondName);

    void readFile() throws IOException, NullPointerException;
}
