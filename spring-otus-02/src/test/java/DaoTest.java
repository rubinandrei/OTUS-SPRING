import org.junit.Before;
import org.junit.Test;
import questionnaire.dao.CSVDaoImpl;
import questionnaire.dao.UserDaoImpl;


import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


public class DaoTest {


    private String pathQuestionCSV = "test_questions.csv";

    @Before
    public void init() {

    }


    @Test
    public void questionsDaoTest() {
        CSVDaoImpl dao = new CSVDaoImpl(pathQuestionCSV);
        try {
            dao.readFile();
            assertThat(dao.getQuestions(), hasSize(1));
            assertThat(dao.getQuestions().get(0).getAnswers(), hasSize(1));

            assertThat(dao.getQuestions(), contains(allOf(hasProperty("question", is("What is JAVA?")),
                    hasProperty("id", is(1)))));

            assertThat(dao.getQuestions().get(0).getAnswers(), contains(allOf(hasProperty("id", is(1)),
                    hasProperty("questionsID", is(1)),
                    hasProperty("answer", is("Java is a high-level programming language and is platform-independent")))));


        } catch (IOException e) {
            assertThat("problem with read file ", false);
        }
    }

    @Test
    public void userDaoTest() {
        UserDaoImpl dao = new UserDaoImpl();
        dao.setUser("Andrei", "Rubin");
        dao.setUserCorrectAnswerCount(5);
        assertThat(dao.getUserFirstName(), is("Andrei"));
        assertThat(dao.getUserLastName(), is("Rubin"));
        assertThat(dao.getUserCorrectAnswer(), is(5));
    }
}
