package PI.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import PI.spring.dao.UserDao;
import PI.spring.model.Login;
import org.springframework.stereotype.Controller;
import PI.spring.model.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    public UserDao userDao;

    @RequestMapping(value="login", method= RequestMethod.GET)
    public String login(Model model){
        model.addAttribute("login", new Login());
        model.addAttribute("message","dasdad");
        model.addAttribute("user", new User());

        return "login";
    }

    @RequestMapping(value="login", method= RequestMethod.POST)
    public String loginPOST(HttpSession session, Model model, @ModelAttribute Login login){
        User user = userDao.validateUser(login);
        if (user == null)
            return "loginFailed";

        session.setAttribute("user",user);
        model.addAttribute("user_name",user.getLogin());

        return "loginSuccessful";
    }

}
