package questionnaire.dao;

public interface UserDao {
    String getUserFirstName();
    String getUserLastName();
    void setUserCorrectAnswerCount(int count);
    int getUserCorrectAnswer();
    void setUser(String firstName,String lastName);
}
