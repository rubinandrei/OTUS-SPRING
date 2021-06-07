import org.junit.Before;
import org.junit.Test;
import questionnaire.dao.CSVDaoImpl;
import questionnaire.dto.Answer;
import questionnaire.dto.Question;

import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class DaoExeptionTest {
    private CSVDaoImpl dao;
    private String pathQuestionCSV = "QuizCSV/1questions.csv";
    private String pathAnswerCSV = "QuizCSV/1answers.csv";

    @Before
    public void init() {
        dao = new CSVDaoImpl(this.pathQuestionCSV);
    }

    @Test
    public void questionsExceptionTest() {
        List<Question> question = dao.getQuestions();
        assertThat(question, is(empty()));
    }

    @Test
    public void answerExceptionTest() {
        List<Answer> answer = dao.getAnswer();
        assertThat(answer, is(empty()));
    }
}
