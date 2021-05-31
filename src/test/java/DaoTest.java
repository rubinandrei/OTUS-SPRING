import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import questionnaire.dao.CSVDaoImpl;
import questionnaire.dto.Answer;
import questionnaire.dto.Question;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;

public class DaoTest {

    private CSVDaoImpl dao;
    private String pathQuestionCSV = "QuizCSV/questions.csv";
    private String pathAnswerCSV = "QuizCSV/answers.csv";
    private List<Answer> answerMock = List.of(new Answer(1, 1, "mock question", true));

    @Before
    public void init() {
        dao = Mockito.spy(new CSVDaoImpl(this.pathQuestionCSV, this.pathAnswerCSV));
    }

    @Test
    public void questionsTest() {
        when(dao.getAnswer()).thenReturn(this.answerMock);
        List<Question> questionList = dao.getQuestions();
        assertThat(questionList, hasSize(5));
        assertThat(questionList.get(0).getQuestion(), is("What is JAVA?"));
        assertThat(questionList.get(0).getAnswers(), hasSize(1));
        assertThat(questionList.get(0).getAnswers(), is(equalTo(answerMock)));
    }

    @Test
    public void answerTest() {
        List<Answer> questionList = dao.getAnswer();
        assertThat("wrong answers count", questionList, hasSize(17));
    }
}
