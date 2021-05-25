package com.ifhc.service;

import com.github.pagehelper.PageInfo;
import com.ifhc.entity.Article;
import com.ifhc.entity.ArticleName;
import com.ifhc.entity.View;

import java.util.List;
import java.util.TreeMap;

public interface ArticleService {
    public PageInfo ArticleSearch(String word,String inlineRadioOptions,Integer pageNum);
    public PageInfo articleSearchcCache(String word,String inlineRadioOptions,Integer pageNum);
    public PageInfo ArticleSearch(String word,Integer pageNum);
    public Article ArticleView(String docno);
    public Article ArticleViewById(Integer id);
    public List<ArticleName> ArticleRecommendByRandom();
    public List<ArticleName> ArticleNameRandom();
    public List<ArticleName> ArticleRecommend(Integer id);
    public TreeMap<Double,ArticleName> ArticleSimilar(Integer id);
    public int saveOrUpdate(View view);
}
