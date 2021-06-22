package com.questionnare.services;

import com.questionnare.localization.LocalizationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ConsoleQuizImpl implements ConsoleQuiz{

    private QuizService quizService;
    private LocalizationServiceImpl local;
    private IOService io;

    @Autowired
    public ConsoleQuizImpl(QuizServiceImpl quizService, LocalizationServiceImpl local, IOServiceImpl io){
        this.local = local;
        this.quizService = quizService;
        this.io = io;
    }


    @Override
    @EventListener(ApplicationStartedEvent.class)
    public void startQuiz() throws IOException {
        quizService.initQuiz();
        io.printString(local.localize("start.testing"));
        quizService.setUser(local.localize("input.name"));
        io.printString(local.localize("greeting",quizService.getUser()));
        quizService.showQuiz();
        quizService.calcResult();
        io.printString(local.localize("score",quizService.showResult().stream().toArray()));
        io.printString(local.localize("congratulations",quizService.showResult().stream().toArray()));

    }
}
