package questionnaire.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import questionnaire.dao.DaoFactory;
import questionnaire.dao.UserDao;
import questionnaire.dto.Answer;
import questionnaire.dto.Question;
import java.util.List;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QuizServiseImpl implements QuizService {

    @Value("${max.passValue}")
    private int passValue;

    private static final String QUIZ_OUT = "\n %s \n";
    private static final String ANSWER_OUT = "\t %d.  -  %s \n\n";
    private static final String START_QUIZ = "\n\n\t %s %s Start quiz:\n\n";

    private final List<Question> question;
    private final List<Answer> answers;
    private final UserDao userDao;
    private final IOServiceImpl inOut;


    @Autowired
    public QuizServiseImpl(DaoFactory daoFactory, IOServiceImpl inOut) {
        this.question = daoFactory.getCsvDao().getQuestions();
        this.answers = daoFactory.getCsvDao().getAnswer();
        this.userDao = daoFactory.getUserDao();
        this.inOut = inOut;
    }

    @Override
    public void showQustions() {
        inOut.printString(String.format(START_QUIZ,userDao.getUserFirstName(),userDao.getUserLastName()));
        AtomicInteger answerNb = new AtomicInteger();
        question.stream().forEach(question -> {
            answerNb.set(0);
            inOut.printString(String.format(QUIZ_OUT, question.getQuestion()));
            question.getAnswers().stream().forEach(answer -> {
                inOut.printString(String.format(ANSWER_OUT, answerNb.incrementAndGet(), answer.getAnswer()));
            });
            question.setAnswerID(inOut.readQuestionAnswer(answerNb.get())-1);
        });
    }

    @Override
    public void setUser() {
        inOut.printString("Please write your name");
        String firstName = inOut.readString();
        inOut.printString("Please write your last name");
        String lastName = inOut.readString();
        userDao.setUser(firstName,lastName);
    }

    private void calcResult(){
        int count = (int) question.stream().filter(question -> question.getAnswers().get(question.getAnswerID()).isCorrect()).count();
        userDao.setUserCorrectAnswerCount(count);
    }
    public void printResult(){
       calcResult();
       inOut.printString("Correct answers: "+ userDao.getUserCorrectAnswer());
    }

}
