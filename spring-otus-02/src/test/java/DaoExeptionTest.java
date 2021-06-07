import org.junit.Before;
import org.junit.Test;
import questionnaire.dao.CSVDaoImpl;

import java.io.IOException;


import static org.hamcrest.MatcherAssert.assertThat;


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
