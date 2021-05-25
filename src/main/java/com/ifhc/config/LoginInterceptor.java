package com.ifhc.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 用于存储排除拦截的url  （登录/login.html, /css,/js,/img）
     */
    private List<String> urls = new ArrayList<String>();
    /**
     * 进入控制器之前拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser") != null) {
            //已登录，放行。。
            return true;
        } else {
            System.out.println("你还没登录，没有权限");
            /* response.sendRedirect("/no_login");*/
      /*  session.setAttribute("no_login",session);
            request.setAttribute("msg", "没有权限，先登录");*/
            //未登录，拦截 返回login
            response.sendRedirect("/login");    //未登录，拦截跳转到登录页
            return false;
        }
    }
    /*设置能通过的url*/
    public List<String> getUrls() {
        urls.add("/user/login");   //login url请求
        urls.add("/login");   //login url请求
        urls.add("/user/out");
//        urls.add("/doLogin");  //登录请求
//        urls.add("/no_login");
//        urls.add("/swagger-ui.html");
        //静态资源
        urls.add("/img/*");
        urls.add("/js/*");
        urls.add("/css/*");
        return urls;
    }
}