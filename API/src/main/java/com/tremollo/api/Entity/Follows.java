package com.tremollo.api.Entity;

import javax.persistence.*;
import java.io.Serializable;

class FollowsId implements Serializable{
    Integer followerId;
    Integer followedId;

    public FollowsId(Integer followerId, Integer followedId) {
        this.followerId = followerId;
        this.followedId = followedId;
    }

    public FollowsId(){}
}

@Entity
@Table(name = "\"ufollowsu\"")
@IdClass(FollowsId.class)
public class Follows {

    @Id
    @Column(name = "follower_user_id")
    Integer followerId;

    @Id
    @Column(name = "followed_user_id")
    Integer followedId;

    public Follows(Integer followerId, Integer followedId) {
        this.followerId = followerId;
        this.followedId = followedId;
    }

    public Follows(){}

    public Integer getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Integer followerId) {
        this.followerId = followerId;
    }

    public Integer getFollowedId() {
        return followedId;
    }

    public void setFollowedId(Integer followedId) {
        this.followedId = followedId;
    }

    @Override
    public String toString() {
        return "Follows{" +
                "userId=" + followerId +
                ", pageId=" + followedId +
                '}';
    }
}
