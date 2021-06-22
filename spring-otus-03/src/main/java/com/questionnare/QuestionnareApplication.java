package com.questionnare;

import com.questionnare.dao.QuizDaoImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class QuestionnareApplication {

    public static void main(String[] args) {
        System.out.println("Ввод неверный. Пожалуйста, попробуйте еще раз");
        SpringApplication.run(QuestionnareApplication.class, args);

    }

}
