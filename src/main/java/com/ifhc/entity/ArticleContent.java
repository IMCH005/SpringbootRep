package com.ifhc.entity;

public class ArticleContent {
    String docno;
    String content;

    public String getDocno() {
        return docno;
    }

    public void setDocno(String docno) {
        this.docno = docno;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ArticleContent{" +
                "docno='" + docno + '\'' +
                ", title='" + content + '\'' +
                '}';
    }
}
