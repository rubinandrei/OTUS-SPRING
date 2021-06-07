package questionnaire.dto;

public final class Answer {
    private int id;
    private int questionsID;
    private String answer;
    private boolean isCorrect;

    public Answer(int id, int questionsID, String answer, boolean isCorrect) {
        this.id = id;
        this.questionsID = questionsID;
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionsID() {
        return questionsID;
    }

    public void setQuestionsID(int questionsID) {
        this.questionsID = questionsID;
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

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}