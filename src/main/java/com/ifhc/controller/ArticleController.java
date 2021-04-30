package com.ifhc.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ifhc.entity.Article;
import com.ifhc.entity.ArticleContent;
import com.ifhc.entity.ArticleName;
import com.ifhc.service.ArticleService;
import com.ifhc.util.BCUtil;
import com.ifhc.util.JBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisTemplate<String,PageInfo> pageInfoRedisTemplate;

    @Value("${selfconfig.pageSize}")
    private int pageSize;

    @RequestMapping("/index")
    public String articleRandom(Model model) {

        List<ArticleName> articleNames = articleService.ArticleNameRandom();
//        for (ArticleName articleName : articleNames) {
//            System.out.println(articleName);
//        }
        model.addAttribute("articleNames", articleNames);
        return "index";
    }

    @RequestMapping("/search")
    public String articleSearch(Model model,
                                @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
//                                @RequestParam(defaultValue="20",value="pageSize")Integer pageSize,
                                String word,
                                @RequestParam(required = false,defaultValue = "option1") String inlineRadioOptions){
//        if(inlineRadioOptions.equals("option2")){
//            if (word.equals("陈浩")){
//                System.out.println("成功了没啊,宝贝儿");
//                model.addAttribute("pageInfo",pageInfoRedisTemplate.opsForValue().get(word));
//                model.addAttribute("word",word);
//                return "search";
//            }
//        }
        if(word==null){
            return "index";
        }
        long startTime =  System.currentTimeMillis();
        PageInfo<ArticleName> objectPageInfo = new PageInfo<ArticleName>();
        if(inlineRadioOptions==null){
            objectPageInfo=articleService.ArticleSearch(word,pageNum);
        }else{
            if(pageNum==1){
                objectPageInfo=articleService.articleSearchcCache(word, inlineRadioOptions, pageNum);
            }else{
                objectPageInfo=articleService.ArticleSearch(word, inlineRadioOptions,pageNum);
            }
        }

        model.addAttribute("word",word);
        model.addAttribute("pageInfo",objectPageInfo);
        model.addAttribute("taketime",System.currentTimeMillis()-startTime);


        return "search";
    }

    @RequestMapping("/view")
    public String viewArticle(Model model, String docno) {
        Article article = articleService.ArticleView(docno);
        model.addAttribute("article", article);

        List<ArticleName> articleNames = articleService.ArticleRecommendByRandom();
        model.addAttribute("articleNames", articleNames);
        return "view";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }

}
