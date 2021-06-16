import org.junit.Before;
import org.junit.Test;
import questionnaire.dao.QuizDaoImpl;
import questionnaire.services.IOServiceImpl;
import questionnaire.services.IOStreamFactory;
import questionnaire.services.QuizServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class InOutServiceTest {

    private QuizDaoImpl dao;
    private IOServiceImpl ioService;
    private QuizServiceImpl quizService;
    private static final String USER_NAME = "Andrei Andreev";

    @Before
    public void init() throws IOException {
        System.setIn(new ByteArrayInputStream(USER_NAME.getBytes()));
        dao = new QuizDaoImpl("test_questions.csv");
        dao.readFile();
        ioService = new IOServiceImpl(new IOStreamFactory());
        quizService = new QuizServiceImpl(dao, ioService);

    }

    @Test
    public void readAnswerTest() throws IOException {
        quizService.setUser();
        assertThat(dao.getUser().getFirstName(), is(USER_NAME.split(" ")[0]));
        assertThat(dao.getUser().getLastName(), is(USER_NAME.split(" ")[1]));
    }


}
