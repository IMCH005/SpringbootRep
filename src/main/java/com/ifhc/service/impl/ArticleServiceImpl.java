package com.ifhc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ifhc.entity.Article;
import com.ifhc.entity.ArticleContent;
import com.ifhc.entity.ArticleName;
import com.ifhc.mapper.ArticleMapper;
import com.ifhc.service.ArticleService;
import com.ifhc.util.JBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisTemplate<String,PageInfo> pageInfoRedisTemplate;

    @Value("${selfconfig.pageSize}")
    private int pageSize;



    @Override
    public PageInfo ArticleSearch(String word,Integer pageNum) {
        PageInfo<ArticleName> pageInfo=new PageInfo<>();
        System.out.println("++++++++++++++++++++++"+pageInfo==null);
        List<ArticleName> articleNames=new ArrayList<>();
        try{
            //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
            //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
            PageHelper.startPage(pageNum,pageSize);

            articleNames =articleMapper.searchNews(word);

            System.out.println("无参数的分页数据："+articleNames);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            pageInfo = new PageInfo<ArticleName>(articleNames,pageSize);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            PageHelper.clearPage();
        }
        return pageInfo;
    }

    @Override
    public PageInfo ArticleSearch(String word,String inlineRadioOptions,Integer pageNum) {
        PageInfo<ArticleName> pageInfo=new PageInfo<>();
        List<ArticleName> articleNames=new ArrayList<>();
//        System.out.println("++++++++++++++++++++++"+pageInfo.size);

        try{
            //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
            //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
            PageHelper.startPage(pageNum,pageSize);
            if(inlineRadioOptions.equals("option1")){
                System.out.println("全文搜索");
                articleNames= articleMapper.searchNews(word);
            }else if(inlineRadioOptions.equals("option2")){
                System.out.println("分词搜索"+JBUtil.toWord(word));
                articleNames= articleMapper.searchArticleNameByTfIdf(JBUtil.toWord(word));
            }else if(inlineRadioOptions.equals("option3")){
                System.out.println("like搜索");
                articleNames= articleMapper.searchNewsLike(word);
            }else if(inlineRadioOptions.equals("option4")){
                System.out.println(inlineRadioOptions);
                articleNames=articleMapper.searchArticleNameByBayes(JBUtil.toWord(word));
            }
//            System.out.println("分页数据："+articleNames);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            pageInfo = new PageInfo<ArticleName>(articleNames,pageSize);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            PageHelper.clearPage();
        }
        return pageInfo;

    }

    @Override
    public PageInfo articleSearchcCache(String word, String inlineRadioOptions, Integer pageNum) {
        PageInfo<ArticleName> agent=pageInfoRedisTemplate.opsForValue().get(word);
        if(agent==null){
            PageInfo<ArticleName> pageInfo=ArticleSearch(word, inlineRadioOptions, pageNum);
            pageInfoRedisTemplate.opsForValue().set(word,pageInfo);
            return pageInfo;
        }else{
            System.out.println("ArticleServiceImpl:95❤❤❤❤❤❤❤❤走了缓存❤❤❤❤❤❤❤");
            pageInfoRedisTemplate.opsForValue().get(word);
            return agent;
        }
    }

    @Override
    public Article ArticleView(String docno) {
        return articleMapper.viewNews(docno);
    }

    @Override
    public List<ArticleName> ArticleRecommendByRandom() {
        Random random=new Random();
        int randId=random.nextInt(50000)+1;
        List<ArticleName> articleNames=articleMapper.randomArticleName(randId);
        return articleNames;
    }

    @Override
    public List<ArticleName> ArticleNameRandom() {
        Random random=new Random();
        int randId=random.nextInt(50000)+1;
        List<ArticleName> articleNames=articleMapper.randomArticleName(randId);
        return articleNames;
    }
}
