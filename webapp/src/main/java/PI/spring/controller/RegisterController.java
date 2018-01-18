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
public class RegisterController {

    @Autowired
    public UserDao userDao;

    @RequestMapping(value="register", method= RequestMethod.GET)
    public String register(Model model){
        model.addAttribute("user", new User());

        return "register";
    }

    @RequestMapping(value="register", method= RequestMethod.POST)
    public String registerPOST(HttpSession session, Model model, @ModelAttribute User user){
        Boolean valid_name = new Boolean(false);
        if (userDao.register(user,valid_name)){
            return "registerSuccess";
        }

        String mesasge;
        if(valid_name)mesasge = "Bad register data";
        else mesasge = "Login already taken";
        model.addAttribute("failedRegisterMessage",mesasge );
        return "registerFailed";
    }

}
