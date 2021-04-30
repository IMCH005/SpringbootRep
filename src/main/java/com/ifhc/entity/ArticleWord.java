package com.ifhc.entity;

public class ArticleWord {
    int id;
    int articleId;
    String word;
    int freqs;
    int freqsTitle;
    int freqsContent;

    @Override
    public String toString() {
        return "ArticleWord{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", word='" + word + '\'' +
                ", freqs=" + freqs +
                ", freqsTitle=" + freqsTitle +
                ", freqsContent=" + freqsContent +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFreqs() {
        return freqs;
    }

    public void setFreqs(int freqs) {
        this.freqs = freqs;
    }

    public int getFreqsTitle() {
        return freqsTitle;
    }

    public void setFreqsTitle(int freqsTitle) {
        this.freqsTitle = freqsTitle;
    }

    public int getFreqsContent() {
        return freqsContent;
    }

    public void setFreqsContent(int freqsContent) {
        this.freqsContent = freqsContent;
    }
}
