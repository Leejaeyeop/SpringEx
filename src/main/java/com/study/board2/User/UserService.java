package com.study.board2.User;

import com.study.board2.Board.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;


@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public String prcessRegist(User user, Model model)
    {
        if(user.check_password(model)) {
            try {
                userRepository.save(user);
                model.addAttribute("message", "가입 성공");
                model.addAttribute("searchUrl", "/"); //페이지 이동
            } catch (Exception e) {
                model.addAttribute("message", "아이디가 중복 됩니다.");
                model.addAttribute("searchUrl", "/regist");
            }
        }
        return "message";
    }

    public String initLogin(User user, HttpSession session, Model model)
    {
        if(session.getAttribute("logged_in")!=null) {
            model.addAttribute("message", "이미 로그인 하였습니다.");//이미 로그인 됨
        }
        else {
            user = userRepository.findById(user.getUser_id(),user.getUser_password()); //로그인 시도
            if (user!=null) {
                model.addAttribute("message", "로그인 성공");
                session.setAttribute("logged_in", true);  //로그인 성공
                session.setAttribute("user",user);
                //session.setAttribute("user_id", user.getUser_id());
            } else {
                model.addAttribute("message", "로그인 실패");
            }
        }
        model.addAttribute("searchUrl", "/"); //페이지 이동
        return "message"; //main
    }

    public User getUserInfo(HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        return user;
    }

    public void modifyUser(HttpSession session,User user)
    {
        userRepository.save(user);
        session.setAttribute("user",user);
    }




}
