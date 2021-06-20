package com.tremollo.api.Entity;

import javax.persistence.*;
import java.io.Serializable;

class LikesId implements Serializable {
    Integer userId;
    Integer contentId;

    public LikesId(Integer userId, Integer contentId) {
        this.userId = userId;
        this.contentId = contentId;
    }

    public LikesId(){}
}

@Entity
@Table(name = "\"likes\"")
@IdClass(LikesId.class)
public class Likes {

    @Id
    @Column(name = "user_id")
    Integer userId;

    @Id
    @Column(name = "content_id")
    Integer contentId;

    public Likes(Integer userId, Integer contentId) {
        this.userId = userId;
        this.contentId = contentId;
    }

    public Likes(){}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    @Override
    public String toString() {
        return "Likes{" +
                "userId=" + userId +
                ", contentId=" + contentId +
                '}';
    }
}
