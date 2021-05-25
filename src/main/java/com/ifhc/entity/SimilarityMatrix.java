package com.ifhc.entity;

import java.io.Serializable;

public class SimilarityMatrix implements Serializable {
    private static final long serialVersionUID = 5419202880712351418L;
    private Integer id;
    private Integer articleIdOne;
    private Integer articleIdAnother;
    private Double similarity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleIdOne() {
        return articleIdOne;
    }

    public void setArticleIdOne(Integer articleIdOne) {
        this.articleIdOne = articleIdOne;
    }

    public Integer getArticleIdAnother() {
        return articleIdAnother;
    }

    public void setArticleIdAnother(Integer articleIdAnother) {
        this.articleIdAnother = articleIdAnother;
    }

    public Double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }
}
