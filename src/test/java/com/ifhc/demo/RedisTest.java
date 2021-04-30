package com.ifhc.demo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ifhc.entity.ArticleName;
import com.ifhc.entity.User;
import com.ifhc.mapper.ArticleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {


    @Autowired
    private RedisTemplate<String,PageInfo> pageInfoRedisTemplate;

    @Autowired
    private ArticleMapper articleMapper;

//    @Autowired
//    private RedisTemplate<String, User> redisTemplate;



    @Test
    public void redidemo(){
        List<String> keys=new ArrayList<>();
        keys.add("陈浩");
        List<ArticleName> articleNames=articleMapper.searchArticleNameByTfIdf(keys);
        List<ArticleName> articleName=articleMapper.searchArticleNameByBayes(keys);
        System.out.println(articleNames);
        System.out.println(articleName);
    }

    /**
     * 清空keys
     */
    @Test
    public void deleteCache(){
        Set<String> keys = pageInfoRedisTemplate.keys("*");
        pageInfoRedisTemplate.delete(keys);
    }

    @Test
    public void redidemomo(){
//        User zzq = new User();
//        zzq.setUsername("庄某");
//        zzq.setPassword("888");
////        myredisTemplate.opsForValue().set("haha",zzq);
//        String key="关键词";
        List<String> keys=new ArrayList<>();
        keys.add("陈浩");
        PageHelper.startPage(1,10);
        List<ArticleName> articleNames=articleMapper.searchArticleNameByTfIdf(keys);
        PageInfo<ArticleName> pageInfo = new PageInfo<ArticleName>(articleNames,10);
        System.out.println(pageInfo);

        pageInfoRedisTemplate.opsForValue().set("陈浩",pageInfo);
        System.out.println(pageInfoRedisTemplate.opsForValue().get("陈浩"));

    }
}
