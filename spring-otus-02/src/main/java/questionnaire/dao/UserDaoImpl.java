package questionnaire.dao;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;
import questionnaire.dto.User;

@Repository
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserDaoImpl implements UserDao {

    private User user;

    @Override
    public String getUserFirstName() {
        return this.user.getFirstName();
    }

    @Override
    public String getUserLastName() {
        return this.user.getLastName();
    }

    @Override
    public void setUserCorrectAnswerCount(int count) {
        this.user.setCountCorrectAnswers(count);
    }

    @Override
    public int getUserCorrectAnswer() {
        return this.user.getCountCorrectAnswers();
    }

    @Override
    public void setUser(String firstName, String lastName) {
        this.user = new User(firstName, lastName);
    }
}
