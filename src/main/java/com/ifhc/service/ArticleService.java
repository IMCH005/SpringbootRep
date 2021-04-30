package com.ifhc.service;

import com.github.pagehelper.PageInfo;
import com.ifhc.entity.Article;
import com.ifhc.entity.ArticleContent;
import com.ifhc.entity.ArticleName;

import java.util.List;

public interface ArticleService {
    public PageInfo ArticleSearch(String word,String inlineRadioOptions,Integer pageNum);
    public PageInfo articleSearchcCache(String word,String inlineRadioOptions,Integer pageNum);
    public PageInfo ArticleSearch(String word,Integer pageNum);
    public Article ArticleView(String docno);
    public List<ArticleName> ArticleRecommendByRandom();
    public List<ArticleName> ArticleNameRandom();
}
