import ua.lviv.lgs.dao.service.UserService;
import ua.lviv.lgs.dao.service.impl.UserServiceImpl;
import ua.lviv.lgs.domain.User;

import java.sql.SQLException;

public class MainTest {
    public static void main(String[] args) throws SQLException {

        UserService userService = new UserServiceImpl();
        userService.create(new User("test@test","test","test","test"));


    }
}
