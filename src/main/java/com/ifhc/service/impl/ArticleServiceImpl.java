package com.ifhc.service.impl;

import com.ifhc.entity.Article;
import com.ifhc.entity.ArticleContent;
import com.ifhc.entity.ArticleName;
import com.ifhc.mapper.ArticleMapper;
import com.ifhc.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<ArticleName> ArticleSearch(String word) {
        return articleMapper.searchNews(word);
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
