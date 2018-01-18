package PI.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;

import PI.spring.rowmapper.UserRowMapper;
import PI.spring.model.Login;
import PI.spring.model.User;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class UserDaoReal implements UserDao {
    @Autowired
    DataSource dataSource;

    @Override
    public boolean register(User user, Boolean valid_login) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = String.format("Select * from users where login = '%s'",user.getLogin());
        List<User> users = jdbcTemplate.query(sql,new UserRowMapper());
        if (users.size()>0){valid_login = false; return false;}

        sql = String.format("insert into users(login,password,email,phone) values ('%s','%s','%s',%s)",
                user.getLogin(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone());

        int row_affected = jdbcTemplate.update(sql);
        if(row_affected > 0 ) return true;


        valid_login = true;
        return false;
    }

    @Override
    public User validateUser(Login login) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = String.format("Select * from users where login = '%s' and password = '%s'",login.getUsername(),login.getPassword());
        List<User> users = jdbcTemplate.query(sql,new UserRowMapper());
        if(users.isEmpty())return null;
        return  users.get(0);
    }
}
