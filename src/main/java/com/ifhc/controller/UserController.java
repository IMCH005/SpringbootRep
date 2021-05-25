package com.ifhc.controller;

import com.ifhc.entity.User;
import com.ifhc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public String login(Model model,@RequestParam(value = "id") Integer id){
        if(loginUser==null){
            System.out.println("空用户");
        }else{
            System.out.println("有用户");
        }
        System.out.println(id);


        request.getSession().setAttribute("loginUser",userService.getById(id));
        return "redirect:/index";
    }

    @GetMapping("/out")
    public String outLogin(){
        session.invalidate();
        return "redirect:/login";
    }

}
