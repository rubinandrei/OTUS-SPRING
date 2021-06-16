package questionnaire.dto;

public class Answer {
    private final int id;
    private final int questionsID;
    private String answer;
    private final boolean isCorrect;

    public Answer(int id, int questionsID, String answer, boolean isCorrect) {
        this.id = id;
        this.questionsID = questionsID;
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public int getId() {
        return id;
    }


    public int getQuestionsID() {
        return questionsID;
    }


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}