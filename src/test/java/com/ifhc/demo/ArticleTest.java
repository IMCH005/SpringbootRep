package com.ifhc.demo;

import com.ifhc.entity.Article;
import com.ifhc.entity.ArticleName;
import com.ifhc.mapper.ArticleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleTest {
    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void testRandomArticle(){
//        System.out.println(articleMapper);

        Random random = new Random();
        int i=random.nextInt(50000)+1;
        System.out.println(i);
        List<Article> articles=articleMapper.randomNews(i);

        for (Article article : articles) {
            System.out.println(article.toString());
        }
    }

    @Test
    public void testSearchArticle(){
        List<ArticleName> articleNames=articleMapper.searchNews("教育");
        for (ArticleName articleName : articleNames) {
            System.out.println(articleName);
        }
    }

    @Test
    public void testViewArticle(){
//        ArticleContent articleContents=articleMapper.viewNews("0000444b941ece98-ebc13306c0bb3300");
//        for (ArticleContent articleContent : articleContents) {
//            System.out.println(articleContent);
//        }
    }
}
