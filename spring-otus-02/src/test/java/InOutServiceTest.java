import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import questionnaire.dao.CSVDaoImpl;
import questionnaire.dao.DaoFactory;
import questionnaire.dao.UserDaoImpl;
import questionnaire.services.IOServiceImpl;
import questionnaire.services.IOStreamFactory;
import questionnaire.services.QuizServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class InOutServiceTest {

    private CSVDaoImpl dao;
    private UserDaoImpl userDao;
    private DaoFactory daoFactory;
    private IOServiceImpl ioService;
    private QuizServiceImpl quizService;
    private static final String USER_NAME = "Andrei Andreev";

    @Before
    public void init() throws IOException {
        System.setIn(new ByteArrayInputStream(USER_NAME.getBytes()));
        dao = new CSVDaoImpl("test_questions.csv");
        userDao = new UserDaoImpl();
        daoFactory = new DaoFactory();
        dao.readFile();
        daoFactory.setCsvDaoFactory(dao);
        daoFactory.setUserDao(userDao);
        ioService = new IOServiceImpl(new IOStreamFactory());
        quizService = new QuizServiceImpl(daoFactory,ioService);

    }

    @Test
    public void readAnswerTest() throws IOException {
        quizService.setUser();
        assertThat(userDao.getUserFirstName(),is(USER_NAME.split(" ")[0]));
        assertThat(userDao.getUserLastName(),is(USER_NAME.split(" ")[1]));
    }


}
