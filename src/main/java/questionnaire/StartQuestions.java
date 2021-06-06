package questionnaire;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import questionnaire.services.QuizService;

@ComponentScan
@Configuration
@PropertySource("classpath:quiz.properties")
public class StartQuestions {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(StartQuestions.class);
        QuizService quiz = context.getBean(QuizService.class);
        quiz.showQustions();

    }
}
