package com.ifhc.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ifhc.entity.Article;
import com.ifhc.entity.ArticleContent;
import com.ifhc.entity.ArticleName;
import com.ifhc.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DemoController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/index")
    public String articleRandom(Model model) {
        List<Article> articles = articleService.ArticleRecommendByRandom();
        for (Article article : articles) {
            System.out.println(article.toString());
        }
        model.addAttribute("articles", articles);
        return "index";
    }

    @RequestMapping("/search")
    public String articleSearch(Model model,
                                @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                                @RequestParam(defaultValue="5",value="pageSize")Integer pageSize,
                                String word){
        if(pageNum == null){
            pageNum = 1;   //设置默认当前页
        }
        if(pageNum <= 0){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 5;    //设置默认每页显示的数据数
        }
        System.out.println("当前页是："+pageNum+"显示条数是："+pageSize);

        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum,pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            List<ArticleName> articleNames = articleService.ArticleSearch(word);
            System.out.println("分页数据："+articleNames);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<ArticleName> pageInfo = new PageInfo<ArticleName>(articleNames,pageSize);
            //4.使用model/map/modelandview等带回前端
            model.addAttribute("pageInfo",pageInfo);
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        //5.设置返回的jsp/html等前端页面
        // thymeleaf默认就会拼串classpath:/templates/xxxx.html
        return "";

    }

    @RequestMapping("/view")
    public String viewArticle(Model model, String docno) {
//      System.out.println("/view Controller活动。。"+docno);
        ArticleContent articleContent = articleService.ArticleView(docno);
        model.addAttribute("articleContent", articleContent);

        List<Article> articles = articleService.ArticleRecommendByRandom();
        for (Article article : articles) {
            System.out.println(article.toString());
        }
        model.addAttribute("articles", articles);

        return "view";
    }
}
