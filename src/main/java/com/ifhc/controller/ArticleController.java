package com.ifhc.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ifhc.entity.Article;
import com.ifhc.entity.ArticleContent;
import com.ifhc.entity.ArticleName;
import com.ifhc.entity.View;
import com.ifhc.service.ArticleService;
import com.ifhc.util.BCUtil;
import com.ifhc.util.JBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.error.Mark;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class ArticleController extends BaseController{

    @RequestMapping("/index")
    public String articleRandom(Model model) {

        List<ArticleName> articleNames = articleService.ArticleRecommend(loginUser.getId());
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
    public String viewArticle(Model model, Integer id) {
//        Article article = articleService.ArticleView(docno);
//        model.addAttribute("article", article);
        Article article=articleService.ArticleViewById(id);
        model.addAttribute("article",article);

        long l = System.currentTimeMillis();
        TreeMap<Double,ArticleName> returnMap=articleService.ArticleSimilar(id);
        model.addAttribute("returnMap",returnMap);

        model.addAttribute("takeTime",System.currentTimeMillis()-l);

        View mark=new View();
        mark.setUserId(loginUser.getId());
        mark.setArticleId(id);
        View mark1=viewMapper.getByView(mark);
        if(mark1!=null){
            if(mark1.getQuality()==null)
                mark1.setQuality(0);
            if(mark1.getInterest()==null)
                mark1.setInterest(0);
            model.addAttribute("mark",mark1);
        }else{
            mark.setInterest(0);
            mark.setQuality(0);
            model.addAttribute("mark",mark);
        }

//        List<ArticleName> articleNames = articleService.ArticleRecommendByRandom();
//        model.addAttribute("articleNames", articleNames);
        return "view";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }



}
