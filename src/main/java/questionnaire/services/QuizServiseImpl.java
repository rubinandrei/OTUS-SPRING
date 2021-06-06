package questionnaire.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import questionnaire.dao.CSVDao;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QuizServiseImpl implements QuizService {

    private static final String QUIZ_OUT = "\n %s \n";
    private static final String ANSWER_OUT = "\t %d.  -  %s \n\n";

    private final CSVDao csvDao;
    private final InOutServices inOutServices;

    @Autowired
    public QuizServiseImpl(CSVDao csvDao, InOutServices inOut) {
        this.csvDao = csvDao;
        this.inOutServices = inOut;
    }

    @Override
    public void showQustions() {
        inOutServices.print("Start quiz:");
        AtomicInteger answerNb = new AtomicInteger();
        csvDao.getQuestions().stream().forEach(question -> {
            answerNb.set(0);
            inOutServices.print(String.format(QUIZ_OUT, question.getQuestion()));
            question.getAnswers().stream().forEach(answer -> {
                inOutServices.print(String.format(ANSWER_OUT, answerNb.incrementAndGet(), answer.getAnswer()));
            });
            inOutServices.readAnswer(answerNb.get());
        });

    }
}
