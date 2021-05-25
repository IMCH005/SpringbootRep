package com.ifhc.controller;

import com.ifhc.entity.View;
import com.ifhc.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class MarkController extends BaseController{


    @PostMapping("/rating")
    public String rating(View view){
        System.out.println(view);
        view.setUserId(loginUser.getId());
        int i=articleService.saveOrUpdate(view);
        System.out.println(i);
        return "评分成功";
    }
}
