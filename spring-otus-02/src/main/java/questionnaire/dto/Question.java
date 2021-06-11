package questionnaire.dto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Question implements Cloneable{
    private final int id;
    private final String question;
    private List<Answer> answers;
    private int answerID;

    public Question(int id, String question) {
        this.id = id;
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }


    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        if (!Objects.isNull(answers)) {
            this.answers = answers.stream()
                    .filter(x -> x.getQuestionsID() == this.id)
                    .collect(Collectors.toList());
        }

    }

    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public Question clone(){
        try {
            return (Question) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
