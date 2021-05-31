package questionnaire.dao;

import questionnaire.dto.Answer;
import questionnaire.dto.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import java.util.stream.Collectors;

public class CSVDaoImpl implements CSVDao {
    final static Logger logger = Logger.getLogger(CSVDaoImpl.class);

    private String questionFile;
    private String answerFile;

    public CSVDaoImpl(String questionFile, String answerFile) {
        this.questionFile = questionFile;
        this.answerFile = answerFile;
    }

    @Override
    public List<Question> getQuestions() {
        List<Answer> answers = getAnswer();
        List<Question> questions = new ArrayList<>();
        try (InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(questionFile);
             BufferedReader questionReader = new BufferedReader(new InputStreamReader(stream))) {
            questions = questionReader.lines()
                    .map(q -> q.split(";"))
                    .filter(q -> q.length > 1)
                    .map(q -> {
                        Question question = new Question(Integer.parseInt(q[0]), q[1]);
                        question.setAnswers(answers);
                        return question;
                    })
                    .collect(Collectors.toList());
        } catch (NullPointerException ex) {
            logger.error(ex);
        } catch (IOException e) {
            logger.error(e);
        }
        return questions;
    }

    @Override
    public List<Answer> getAnswer() {
        List<Answer> answers = new ArrayList<>();
        try (InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(answerFile);
             BufferedReader questionReader = new BufferedReader(new InputStreamReader(stream))) {
            answers = questionReader.lines()
                    .map(q -> q.split(";"))
                    .filter(q -> q.length > 3)
                    .map(q -> {
                        return new Answer(Integer.parseInt(q[0]), Integer.parseInt(q[1]), q[2], Boolean.parseBoolean(q[3]));

                    })
                    .collect(Collectors.toList());
        } catch (NullPointerException ex) {
            logger.error(ex);
        } catch (IOException e) {
            logger.error(e);
        }

        return answers;
    }


}
