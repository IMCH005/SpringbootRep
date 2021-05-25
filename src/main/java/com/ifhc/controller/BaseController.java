package com.ifhc.controller;

import com.github.pagehelper.PageInfo;
import com.ifhc.entity.User;
import com.ifhc.mapper.ViewMapper;
import com.ifhc.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {
    @Autowired
     ArticleService articleService;

    @Autowired
     RedisTemplate<String, PageInfo> pageInfoRedisTemplate;

    @Autowired
    ViewMapper viewMapper;

    @Value("${selfconfig.pageSize}")
     int pageSize;

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    protected User loginUser;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession(true);
        loginUser = (User) session.getAttribute("loginUser");
    }
}
