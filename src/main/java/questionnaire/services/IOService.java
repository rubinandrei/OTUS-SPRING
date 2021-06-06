package questionnaire.services;

public interface IOService {
    void printString(String string);

    public String readString();

    public int readQuestionAnswer(int count);

}