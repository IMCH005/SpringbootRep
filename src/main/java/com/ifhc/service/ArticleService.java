package com.ifhc.service;

import com.ifhc.entity.Article;
import com.ifhc.entity.ArticleContent;
import com.ifhc.entity.ArticleName;

import java.util.List;

public interface ArticleService {
    public List<ArticleName> ArticleSearch(String word);
    public Article ArticleView(String docno);
    public List<ArticleName> ArticleRecommendByRandom();
    public List<ArticleName> ArticleNameRandom();
}
