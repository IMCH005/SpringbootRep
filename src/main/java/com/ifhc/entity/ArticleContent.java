package com.ifhc.entity;

import java.io.Serializable;

public class ArticleContent implements Serializable {
    private static final long serialVersionUID = -2804634544431358950L;
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
