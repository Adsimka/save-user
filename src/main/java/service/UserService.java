package service;

import dao.UserDao;
import model.Gender;
import model.User;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserService {

    private UserDao userDao;

    private DateTimeFormatter formatter;

    private static final UserService INSTANCE = new UserService();

    private UserService() {
        userDao = UserDao.getInstance();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public void saveUserDB(HttpServletRequest req) {
        String name = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        LocalDate dateOfBirth = getBirthday(req);
        String country = req.getParameter("country");
        Gender gender = getGender(req);

        userDao.saveUser(new User(name, lastName, dateOfBirth, country, gender));
    }

    private LocalDate getBirthday(HttpServletRequest req) {
        String dateOfBirth = req.getParameter("bday");
        return LocalDate.parse(dateOfBirth, formatter);
    }

    private Gender getGender(HttpServletRequest req) {
        String gender = req.getParameter("gender").toUpperCase();
        if (gender.equals(Gender.FEMALE.toString())) {
            return Gender.FEMALE;
        }
        return Gender.MALE;
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
