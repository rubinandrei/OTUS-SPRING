package questionnaire.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DaoFactory {

    private CSVDao csvDao;
    private UserDao userDao;

    @Autowired
    public void csvDaoFactory(CSVDao csvDao){
        this.csvDao =csvDao;
    }

    @Autowired
    public void getUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    public CSVDao getCsvDao() {
        return csvDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }
}
