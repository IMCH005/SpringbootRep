package com.ifhc.entity;

import java.io.Serializable;
import java.util.List;

public class ArticleName implements Serializable {
    private static final long serialVersionUID = 1717303153559139272L;
    int id;
    String docno;
    String url;
    String title;
    List<ArticleWord> articleWords;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocno() {
        return docno;
    }

    public void setDocno(String docno) {
        this.docno = docno;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ArticleWord> getArticleWords() {
        return articleWords;
    }

    public void setArticleWords(List<ArticleWord> articleWords) {
        this.articleWords = articleWords;
    }

    @Override
    public String toString() {
        return "ArticleName{" +
                "id=" + id +
                ", docno='" + docno + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", articleWords=" + articleWords +
                '}';
    }
}
