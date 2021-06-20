package com.tremollo.api.Entity;

import javax.persistence.*;
import java.io.Serializable;

class ProLikesId implements Serializable {
    Integer userId;
    Integer contentId;

    public ProLikesId(Integer userId, Integer contentId) {
        this.userId = userId;
        this.contentId = contentId;
    }

    public ProLikesId(){}
}

@Entity
@Table(name = "\"pro_likes\"")
@IdClass(LikesId.class)
public class ProLikes {

    @Id
    @Column(name = "user_id")
    Integer userId;

    @Id
    @Column(name = "content_id")
    Integer contentId;

    public ProLikes(Integer userId, Integer contentId) {
        this.userId = userId;
        this.contentId = contentId;
    }

    public ProLikes(){}

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