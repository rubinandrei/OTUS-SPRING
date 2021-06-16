package questionnaire.services;

public interface IOService {
    void printString(String string);

    String[] readUser();

    int readQuestionAnswer(int count);

}