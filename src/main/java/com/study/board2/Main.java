package com.study.board2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class Main {

   // private String user_id;
    private boolean logged_in;

    @GetMapping("/")
    public String Main(HttpSession session)
    {
        try {
            logged_in = (boolean)session.getAttribute("logged_in");
        }
        catch (Exception e) {logged_in=false;}

        if(!logged_in) return "user/UserLogin";
        else return "main";
        //return "main";
    }
}
