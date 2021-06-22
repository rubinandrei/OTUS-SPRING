package com.questionnare.services;

public interface IOService {
    void printString(String string);

    String[] readUser(String message);

    int readQuestionAnswer(int count);

}