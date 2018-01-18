package PI.spring.dao;


import PI.spring.model.Login;
import PI.spring.model.User;

public interface UserDao {
    boolean register(User user,Boolean valid_login);
    User validateUser(Login login);
}