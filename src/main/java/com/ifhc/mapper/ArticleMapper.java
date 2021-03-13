package com.ifhc.mapper;

import com.ifhc.entity.Article;
import com.ifhc.entity.ArticleContent;
import com.ifhc.entity.ArticleName;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {
    List<Article> randomNews(Integer randId);
    List<ArticleName> searchNews(String word);
    ArticleContent viewNews(String docno);
}
