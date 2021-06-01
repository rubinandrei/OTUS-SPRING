package questionnaire.dao;

import questionnaire.dto.Answer;
import questionnaire.dto.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import java.util.stream.Collectors;

public class CSVDaoImpl implements CSVDao {
    final static Logger LOGGER = Logger.getLogger(CSVDaoImpl.class);

    private final String question_file;
    private final String answer_file;

    public CSVDaoImpl(String questionFile, String answerFile) {
        this.question_file = questionFile;
        this.answer_file = answerFile;
    }

    @Override
    public List<Question> getQuestions() {
        List<Answer> answers = getAnswer();
        List<Question> questions = new ArrayList<>();
        try (InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(question_file);
             BufferedReader questionReader = new BufferedReader(new InputStreamReader(stream))) {
            questions = questionReader.lines()
                    .skip(1)
                    .map(q -> q.split(";"))
                    .filter(q -> q.length > 1)
                    .map(q -> {
                        Question question = new Question(Integer.parseInt(q[0]), q[1]);
                        question.setAnswers(answers);
                        return question;
                    })
                    .collect(Collectors.toList());
        } catch (NullPointerException ex) {
            LOGGER.error(ex);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return questions;
    }

    @Override
    public List<Answer> getAnswer() {
        List<Answer> answers = new ArrayList<>();
        try (InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(answer_file);
             BufferedReader questionReader = new BufferedReader(new InputStreamReader(stream))) {
            answers = questionReader.lines()
                    .skip(1)
                    .map(q -> q.split(";"))
                    .filter(q -> q.length > 3)
                    .map(q -> {
                        return new Answer(Integer.parseInt(q[0]), Integer.parseInt(q[1]), q[2], Boolean.parseBoolean(q[3]));

                    })
                    .collect(Collectors.toList());
        } catch (NullPointerException ex) {
            LOGGER.error(ex);
        } catch (IOException e) {
            LOGGER.error(e);
        }

        return answers;
    }


}
