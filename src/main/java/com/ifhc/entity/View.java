package com.ifhc.entity;

import java.io.Serializable;
import java.util.Date;

public class View implements Serializable {
    private static final long serialVersionUID = 7960155732639456382L;

    private Integer id;
    private Integer userId;
    private Integer articleId;
    private Integer interest;
    private Integer quality;
    private Date cdt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getInterest() {
        return interest;
    }

    public void setInterest(Integer interest) {
        this.interest = interest;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public Date getCdt() {
        return cdt;
    }

    public void setCdt(Date cdt) {
        this.cdt = cdt;
    }

    @Override
    public String toString() {
        return "view{" +
                "id=" + id +
                ", userId=" + userId +
                ", articleId=" + articleId +
                ", interest=" + interest +
                ", quality=" + quality +
                ", cdt=" + cdt +
                '}';
    }
}
