package com.questionnare.services;

import com.questionnare.localization.LocalizationService;
import com.questionnare.localization.LocalizationServiceImpl;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ConsoleQuizImpl implements ConsoleQuiz{

    private final QuizService quizService;
    private final LocalizationService local;
    private final IOService io;
    private final UserService user;

    public ConsoleQuizImpl(QuizServiceImpl quizService, LocalizationServiceImpl local, IOServiceImpl io, UserServiceImpl user){
        this.local = local;
        this.quizService = quizService;
        this.io = io;
        this.user = user;
    }


    @Override
    @EventListener(ApplicationStartedEvent.class)
    public void startQuiz() throws IOException {
        quizService.initQuiz();
        io.printString(local.localize("start.testing"));
        user.setQuizUser(local.localize("input.name"));
        io.printString(local.localize("greeting",user.getUserName()));
        user.setUserQuestions(quizService.getQuestion());
        quizService.showQuiz();
        user.calcUserResult();
        io.printString(local.localize("score",user.showUserResults().stream().toArray()));
        io.printString(local.localize("congratulations",""));
    }
}
