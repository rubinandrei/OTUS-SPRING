package questionnaire.dao;

import questionnaire.dto.Answer;
import questionnaire.dto.Question;

import java.util.List;

public interface CSVDao {

    List<Question> getQuestions();

    List<Answer> getAnswer();
}
