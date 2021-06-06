import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import questionnaire.StartQuestions;
import questionnaire.dao.CSVDaoImpl;
import questionnaire.dao.DaoFactory;
import questionnaire.dao.UserDao;
import questionnaire.dao.UserDaoImpl;
import questionnaire.services.IOService;
import questionnaire.services.IOServiceImpl;
import questionnaire.services.IOStreamFactory;
import questionnaire.services.QuizService;
import questionnaire.services.QuizServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;


public class quizServiceTest {

    private CSVDaoImpl dao;
    private UserDaoImpl userDao;
    private DaoFactory daoFactory;
    private IOServiceImpl ioService;
    private QuizServiceImpl quizService;



    @Before
    public void init() throws IOException {
        System.setIn(new ByteArrayInputStream(String.valueOf(1).getBytes()));
        dao = new CSVDaoImpl("test_questions.csv");
        userDao = new UserDaoImpl();
        userDao.setUser("Andrei", "Rubin");
        daoFactory = new DaoFactory();
        dao.readFile();
        daoFactory.setCsvDaoFactory(dao);
        daoFactory.setUserDao(userDao);
        ioService = Mockito.spy(new IOServiceImpl(new IOStreamFactory()));
        quizService = Mockito.spy(new QuizServiceImpl(daoFactory,ioService));

    }

    @Test
    public void quizServiceCalcResultTest(){
        dao.getQuestions().get(0).setAnswerID(1);
        quizService.calcResult();
        assertThat(userDao.getUserCorrectAnswer(),is(1));

    }

    @Test
    public void quizServiceResultTest(){
        dao.getQuestions().get(0).setAnswerID(1);
        quizService.calcResult();
        assertThat(quizService.getResult(),is("ANDREI  RUBIN  YOUR RESULT 1 OF CORRECT ANSWERS \n IT'S 100%"));
    }


    @Test
    public void startQuizTest(){
        quizService.startQuiz();
        assertThat(quizService.getResult(),is("ANDREI  RUBIN  YOUR RESULT 1 OF CORRECT ANSWERS \n IT'S 100%"));
        assertThat(userDao.getUserCorrectAnswer(),is(1));
    }


}
