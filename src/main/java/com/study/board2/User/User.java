package com.study.board2.User;

import lombok.Data;
import org.springframework.ui.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data //getter setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String user_id;
    private String user_password;
    private String user_name;
    private Integer user_age;

    public boolean check_password(Model model)
    {
        final int id_length = 3;
        if(this.user_id.length()<id_length)
        {
            model.addAttribute("message","아이디가 짧습니다.");
            model.addAttribute("searchUrl", "/regist");
            return false;
        }
        else if(!this.user_id.matches(".*[a-zA-Z].*"))
        {
            model.addAttribute("message","아이디에 영문자가 포함되어야 합니다.");
            model.addAttribute("searchUrl", "/regist");
            return false;
        }
        else if(!this.user_password.matches(".*[a-zA-Z].*"))
        {
            model.addAttribute("message","비밀번호에 영문자가 포함되어야 합니다.");
            model.addAttribute("searchUrl", "/regist");
            return false;
        }

        return true;
    }

}
