package com.ifhc.entity;

import java.io.Serializable;

public class Article implements Serializable {
    private static final long serialVersionUID = 1327609803372351546L;
    int id;
    String url;
    String docno;
    String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDocno() {
        return docno;
    }

    public void setDocno(String docno) {
        this.docno = docno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String content;
    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", docno='" + docno + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }


}
