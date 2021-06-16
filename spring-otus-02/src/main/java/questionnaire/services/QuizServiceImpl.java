package questionnaire.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import questionnaire.dao.QuizDao;
import questionnaire.dto.Question;

import java.io.IOException;
import java.util.List;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    @Value("${max.passValue}")
    private int passValue;

    private static final String QUIZ_OUT = "\n %s \n";
    private static final String ANSWER_OUT = "\t %d.  -  %s \n\n";
    private static final String START_QUIZ = "\n\n\t %s %s Start quiz:\n\n";
    private static final String RESULT_FORM = "%s  %s  your result %d of correct answers \n " +
            "it's %d%%";

    private List<Question> questions;
    private final QuizDao dao;
    private final IOServiceImpl inOut;


    @Autowired
    public QuizServiceImpl(QuizDao dao, IOServiceImpl inOut) {
        this.dao = dao;
        this.inOut = inOut;

    }

    public void initQuiz() throws IOException {
        this.dao.readFile();
        questions = dao.getQuestions().stream()
                .map(this::apply)
                .collect(Collectors.toList());
    }
    @Override
    public void startQuiz() throws IOException {
        initQuiz();
        setUser();
        showQuiz();
        calcResult();
        inOut.printString(showResult());
    }

    public void showQuiz(){
        AtomicInteger answerNb = new AtomicInteger();
        questions.forEach(question -> {
            answerNb.set(0);
            inOut.printString(String.format(QUIZ_OUT, question.getQuestion()));
            question.getAnswers().forEach(answer ->
                    inOut.printString(String.format(ANSWER_OUT, answerNb.incrementAndGet(), answer.getAnswer())));
            question.setAnswerID(inOut.readQuestionAnswer(answerNb.get()));
        });
    }

    public void setUser() {
        String[] user = inOut.readUser();
        dao.setUser(user[0], user[1]);
        inOut.printString(String.format(START_QUIZ, dao.getUser().getFirstName(), dao.getUser().getFirstName()));
    }

    public void calcResult() {
        int count = (int) questions.stream()
                .filter(question -> question.getAnswers().get(question.getAnswerID() - 1).isCorrect())
                .count();
        dao.getUser().setCountCorrectAnswers(count);
    }

    public String showResult() {
        String resultFormat = dao.getUser().getCountCorrectAnswers() > 0
                ? String.format(RESULT_FORM,
                dao.getUser().getFirstName(),
                dao.getUser().getLastName(),
                dao.getUser().getCountCorrectAnswers(),
                dao.getUser().getCountCorrectAnswers() * 100 / questions.size())
                : String.format(RESULT_FORM,
                dao.getUser().getFirstName(),
                dao.getUser().getLastName(),
                0, 0);

        return resultFormat.toUpperCase();

    }

    private Question apply(Question x) {
        Question question = x.clone();
        question.setAnswers(dao.getAnswers());
        return question;
    }
}
