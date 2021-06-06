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
public class QuizServiceImpl implements QuizService {

    @Value("${max.passValue}")
    private int passValue;

    private static final String QUIZ_OUT = "\n %s \n";
    private static final String ANSWER_OUT = "\t %d.  -  %s \n\n";
    private static final String START_QUIZ = "\n\n\t %s %s Start quiz:\n\n";
    private static final String RESULT_FORM = "%s  %s  your result %d of correct answers \n " +
                                              "it's %d%%" ;

    private final List<Question> question;
    private final List<Answer> answers;
    private final UserDao userDao;
    private final IOServiceImpl inOut;


    @Autowired
    public QuizServiceImpl(DaoFactory daoFactory, IOServiceImpl inOut) {
        this.question = daoFactory.getCsvDao().getQuestions();
        this.answers = daoFactory.getCsvDao().getAnswer();
        this.userDao = daoFactory.setUserDao();
        this.inOut = inOut;
    }

    @Override
    public void startQuiz() {
        inOut.printString(String.format(START_QUIZ,userDao.getUserFirstName(),userDao.getUserLastName()));
        AtomicInteger answerNb = new AtomicInteger();
        question.stream().forEach(question -> {
            answerNb.set(0);
            inOut.printString(String.format(QUIZ_OUT, question.getQuestion()));
            question.getAnswers().stream().forEach(answer -> {
                inOut.printString(String.format(ANSWER_OUT, answerNb.incrementAndGet(), answer.getAnswer()));
            });
            question.setAnswerID(inOut.readQuestionAnswer(answerNb.get()));
        });
        calcResult();
    }

    @Override
    public void setUser() {
        inOut.printString("Please write your name");
        String firstName = inOut.readString();
        inOut.printString("Please write your last name");
        String lastName = inOut.readString();
        userDao.setUser(firstName,lastName);
    }

    public void calcResult(){
        int count = (int) question.stream()
                .filter(question -> question.getAnswers().get(question.getAnswerID()-1).isCorrect())
                .count();
        userDao.setUserCorrectAnswerCount(count);
    }

    public String getResult(){
        String resultFormat = userDao.getUserCorrectAnswer() > 0
                ? String.format(RESULT_FORM,
                    userDao.getUserFirstName(),
                    userDao.getUserLastName(),
                    userDao.getUserCorrectAnswer(),
                    (int) (userDao.getUserCorrectAnswer() * 100/ question.size()))
                : String.format(RESULT_FORM,
                    userDao.getUserFirstName(),
                    userDao.getUserLastName(),
                    0,0);

        return resultFormat.toUpperCase();

    }

}
