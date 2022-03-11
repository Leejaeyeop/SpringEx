package com.study.board2.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping ("/regist")
    public String UserRegist()
    {
        return "user/UserRegist";
    }

    @PostMapping("/request-regist")
    public String RequestRegist(User user, Model model)
    {
        return userService.prcessRegist(user,model);
    }

    @PostMapping ("/request-login")
    public String RequestLogin(User user,HttpSession session,Model model)
    {
        return userService.initLogin(user,session,model); //main
    }

    @PostMapping ("/logout")
    public String Logout(HttpSession session,Model model)
    {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping ("/user-manager")
    public String UserManager(HttpSession session,Model model)
    {
        if(session.getAttribute("logged_in") != null)
        {
              model.addAttribute("user_info",userService.getUserInfo(session));
              return "user/UserManager";
        }
        else return "main";
    }

    @PostMapping("/user-modify")
    public String UserModify(HttpSession session,User modify_user,Model model)
    {
        User original_user= (User)session.getAttribute("user");
        int m_age= modify_user.getUser_age();
        String m_name = modify_user.getUser_name();

        if(m_age>0) original_user.setUser_age(m_age);
        if(m_name!="") original_user.setUser_name(m_name);

        userService.modifyUser(session,original_user);
        return "main";
    }
}
