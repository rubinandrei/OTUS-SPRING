import org.junit.Before;
import org.junit.Test;
import questionnaire.dao.QuizDaoImpl;

import java.io.IOException;


import static org.hamcrest.MatcherAssert.assertThat;


public class DaoExeptionTest {
    private QuizDaoImpl dao;
    private String pathQuestionCSV = "2questions.csv";


    @Before
    public void init() {
        dao = new QuizDaoImpl(this.pathQuestionCSV);
    }

    @Test(expected = NullPointerException.class)
    public void questionsExceptionTest() throws IOException {
        dao.readFile();
    }


}
