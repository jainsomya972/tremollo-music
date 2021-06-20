package com.tremollo.api.DTO;

import com.tremollo.api.Entity.Page;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class PageDTO {

    String name;
    String avatar;
    String about;
    Date dateCreated;
    Integer pageId;
    String pageType;
    ArrayList<String> members;
    Integer userId;

    public PageDTO(String name, String avatar, String about, Date dateCreated, Integer pageId, String pageType, ArrayList<String> members, Integer userId) {
        this.name = name;
        this.avatar = avatar;
        this.about = about;
        this.dateCreated = dateCreated;
        this.pageId = pageId;
        this.pageType = pageType;
        this.members = members;
        this.userId = userId;
    }

    public PageDTO(){}

    public PageDTO(Page page){
        this.name = page.getName();
        this.avatar = page.getAvatar();
        this.about = page.getAbout();
        this.dateCreated = page.getDateCreated();
        this.pageId = page.getPageId();
        this.pageType = page.getPageType();
        this.userId = page.getUserId();
        String[] raw  = page.getMembers().split(",");
        this.members = new ArrayList<>();
        members.addAll(Arrays.asList(raw));
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

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
