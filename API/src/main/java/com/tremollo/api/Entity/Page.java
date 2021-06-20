package com.tremollo.api.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "\"page\"")
public class Page {

    @Column(name = "name")
    String name;

    @Column(name = "avatar")
    String avatar;

    @Column(name = "about")
    String about;

    @Column(name = "date_created")
    Date dateCreated;

    @Id
    @Column(name = "page_id")
    Integer pageId;

    @Column(name = "type")
    String pageType;

    @Column(name = "members")
    String members;

    @Column(name = "user_id")
    Integer userId;

    public Page(String name, String avatar, String about, Date dateCreated, Integer pageId, String pageType, String members, Integer userId) {
        this.name = name;
        this.avatar = avatar;
        this.about = about;
        this.dateCreated = dateCreated;
        this.pageId = pageId;
        this.pageType = pageType;
        this.members = members;
        this.userId = userId;
    }

    public Page() {
        dateCreated = new Date();
        this.pageType = "personal";
        this.members = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Page{" +
                "name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", about='" + about + '\'' +
                ", dateCreated=" + dateCreated +
                ", pageId=" + pageId +
                ", pageType='" + pageType + '\'' +
                ", members='" + members + '\'' +
                ", userId=" + userId +
                '}';
    }
}
