package questionnaire.services;

import questionnaire.dao.CSVDao;

import java.util.concurrent.atomic.AtomicInteger;

public class QuizServiseImpl implements QuizService{

    private final String quizOut = "\n %s \n";
    private final String answerOut = "\t %d.  -  %s \n\n";

    private CSVDao csvDao;
    private InOutServices inOutServices;

    public QuizServiseImpl(CSVDao csvDao,InOutServices inOut){
        this.csvDao = csvDao;this.inOutServices = inOut;
    }
    @Override
    public void showQustions() {
        System.out.println("Start quiz:");
        AtomicInteger answerNb = new AtomicInteger();
        csvDao.getQuestions().stream().forEach(question -> {
            answerNb.set(0);
            inOutServices.print(String.format(quizOut,question.getQuestion()));
            question.getAnswers().stream().forEach(answer ->{
                inOutServices.print(String.format(answerOut,answerNb.incrementAndGet(),answer.getAnswer()));
            });
            inOutServices.readAnswer(answerNb.get());
        });

    }
}
