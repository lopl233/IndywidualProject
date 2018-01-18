package PI.spring.controller;

import org.springframework.stereotype.Controller;
import PI.spring.model.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @RequestMapping(value="home", method= RequestMethod.GET)
    public String withParam(Model model, HttpSession session) {

        String message;
        User user = (User)session.getAttribute("user");
        if(user == null)
            message = "Hello please login";
        else
            message = user.getLogin();

        model.addAttribute("message",message);

        return "home";
    }




}
