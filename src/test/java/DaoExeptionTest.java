import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import questionnaire.dao.CSVDaoImpl;
import questionnaire.dto.Answer;
import questionnaire.dto.Question;

import java.io.IOException;
import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class DaoExeptionTest {
    private CSVDaoImpl dao;
    private String pathQuestionCSV = "1questions.csv";


    @Before
    public void init() {
        dao = new CSVDaoImpl(this.pathQuestionCSV);
    }

    @Test(expected = NullPointerException.class)
    public void questionsExceptionTest() throws IOException {
        dao.readFile();
    }
    


}
