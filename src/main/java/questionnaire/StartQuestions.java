package questionnaire;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import questionnaire.services.QuizService;

public class StartQuestions {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("questionnaire-context.xml");
        QuizService quiz = context.getBean(QuizService.class);
        quiz.showQustions();

    }
}
