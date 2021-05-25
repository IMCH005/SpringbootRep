package com.ifhc.mapper;

import com.ifhc.entity.Article;
import com.ifhc.entity.ArticleName;
import com.ifhc.entity.ArticleWord;
import com.ifhc.entity.Vocabulary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {
    List<Article> randomNews(Integer randId);
    List<ArticleName> randomArticleName(Integer randId);
    List<ArticleName> searchNews(String word);
    List<ArticleName> searchNewsLike(String word);
    Article viewNews(String docno);
    Article viewNewsById(Integer id);
    void addArticleWord(List<ArticleWord> list);
    void addVoc(List<Vocabulary> list);
    List<ArticleName> searchArticleNameByTfIdf(List<String> words);
    List<ArticleName> searchArticleNameByBayes(List<String> words);
    List<ArticleWord> getArticleWord(@Param(value = "words") List<String> words, @Param(value = "id")Integer articlId);
    List<String> wordByArticleId(@Param(value = "id") Integer id,@Param(value = "dimension") Integer dimension);
    ArticleName getByListDimension(@Param(value = "id")Integer id,@Param(value = "dimension") Integer dimension);
    List<Integer> articleIdByWordList(List<String> words,Integer id);
    String getTitleById(Integer id);
    ArticleName getArticleNameById(Integer articleId);
}
