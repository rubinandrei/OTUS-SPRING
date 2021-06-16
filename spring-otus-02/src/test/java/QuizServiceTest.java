import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import questionnaire.dao.QuizDaoImpl;
import questionnaire.services.IOServiceImpl;
import questionnaire.services.IOStreamFactory;
import questionnaire.services.QuizServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class QuizServiceTest {

    private QuizDaoImpl dao;
    private IOServiceImpl ioService;
    private QuizServiceImpl quizService;


    @Before
    public void init() throws IOException {
        System.setIn(new ByteArrayInputStream(String.valueOf(1).getBytes()));
        dao = new QuizDaoImpl("test_questions.csv");
        dao.setUser("Andrei", "Rubin");
        ioService = Mockito.spy(new IOServiceImpl(new IOStreamFactory()));
        quizService = new QuizServiceImpl(dao, ioService);
        quizService.initQuiz();

    }

    @Test
    public void quizServiceCalcResultTest() {
        System.setIn(new ByteArrayInputStream(String.valueOf(1).getBytes()));
        quizService.showQuiz();
        quizService.calcResult();
        assertThat(dao.getUser().getCountCorrectAnswers(), is(1));

    }

    @Test
    public void quizServiceResultTest() throws IOException {
        System.setIn(new ByteArrayInputStream(String.valueOf(1).getBytes()));
        quizService.showQuiz();
        quizService.calcResult();
        assertThat(quizService.showResult(), is("ANDREI  RUBIN  YOUR RESULT 1 OF CORRECT ANSWERS \n IT'S 100%"));
    }

}
