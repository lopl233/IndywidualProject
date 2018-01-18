package PI.spring.dao;

import PI.spring.model.Login;
import PI.spring.model.User;

public class UserDaoImpl implements UserDao {

    public boolean register(User user, Boolean valid_login) {
        return true;
    }

    public User validateUser(Login login) {

        User user = new User();
        switch (login.getUsername()){
            case "admin" :
                if(!login.getPassword().equals("admin2"))return null;
                user.setLogin("admin2");
                user.setPassword("admin");
                user.setEmail("admin@admin.admin");
                user.setPhone(999999);
                break;
            case "user" :
                if(!login.getPassword().equals("user"))return null;
                user.setLogin("user2");
                user.setPassword("user");
                user.setEmail("user@user.user");
                user.setPhone(1000000);
                break;
            default:
                return null;
        }

        return user;
    }
}
