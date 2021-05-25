package com.ifhc.entity;

import java.io.Serializable;

public class ArticleWord implements Serializable {
    private static final long serialVersionUID = 3044863508053518851L;
    Integer id;
    Integer articleId;
    String word;
    Integer freqs;
    Integer freqsTitle;
    Integer freqsContent;
    Float tfIdf;
    Float bayes;

    @Override
    public String toString() {
        return "ArticleWord{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", word='" + word + '\'' +
                ", freqs=" + freqs +
                ", freqsTitle=" + freqsTitle +
                ", freqsContent=" + freqsContent +
                ", tfIdf=" + tfIdf +
                ", bayes=" + bayes +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getFreqs() {
        return freqs;
    }

    public void setFreqs(Integer freqs) {
        this.freqs = freqs;
    }

    public Integer getFreqsTitle() {
        return freqsTitle;
    }

    public void setFreqsTitle(Integer freqsTitle) {
        this.freqsTitle = freqsTitle;
    }

    public Integer getFreqsContent() {
        return freqsContent;
    }

    public void setFreqsContent(Integer freqsContent) {
        this.freqsContent = freqsContent;
    }

    public Float getTfIdf() {
        return tfIdf;
    }

    public void setTfIdf(Float tfIdf) {
        this.tfIdf = tfIdf;
    }

    public Float getBayes() {
        return bayes;
    }

    public void setBayes(Float bayes) {
        this.bayes = bayes;
    }
}
