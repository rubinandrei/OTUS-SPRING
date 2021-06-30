package com.questionnare.services;

public interface IOService {
    void printString(String string);

    String readConsoleString(String message);

    int readQuestionAnswer(int count);

}