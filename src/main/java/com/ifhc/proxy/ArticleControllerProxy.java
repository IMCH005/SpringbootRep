//package com.ifhc.proxy;
//
//import com.ifhc.util.BCUtil;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.stereotype.Component;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Component
//@Aspect
//public class ArticleControllerProxy {
//    @Around(value = "execution(* com.ifhc.service.ArticleService.ArticleSearch(..))")
//    public Object viewBefore(ProceedingJoinPoint joinPoint) throws Throwable{
//        Object[] args = joinPoint.getArgs();
////        System.out.println(args[0]);
//        args[0]=BCUtil.ToSBC((String)args[0]);
//        Object result= joinPoint.proceed(args);
////        System.out.println(result);
//        return result;
//    }
//}
