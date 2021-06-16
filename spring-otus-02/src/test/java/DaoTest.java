import org.junit.Before;
import org.junit.Test;
import questionnaire.dao.QuizDao;
import questionnaire.dao.QuizDaoImpl;


import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


public class DaoTest {


    private String pathQuestionCSV = "test_questions.csv";
    private QuizDaoImpl dao;

    @Before
    public void init() throws IOException {
        dao = new QuizDaoImpl(pathQuestionCSV);
    }


    @Test
    public void questionsDaoTest() {

        try {
            dao.readFile();
            assertThat(dao.getQuestions(), hasSize(1));

            assertThat(dao.getQuestions(), contains(allOf(hasProperty("question", is("What is JAVA?")),
                    hasProperty("id", is(1)))));
        } catch (IOException e) {
            assertThat("problem with read file ", false);
        }
    }

    @Test
    public void userDaoTest() {
        dao.setUser("Andrei", "Rubin");
        dao.getUser().setCountCorrectAnswers(5);
        assertThat(dao.getUser().getFirstName(), is("Andrei"));
        assertThat(dao.getUser().getLastName(), is("Rubin"));
        assertThat(dao.getUser().getCountCorrectAnswers(), is(5));
    }
}
