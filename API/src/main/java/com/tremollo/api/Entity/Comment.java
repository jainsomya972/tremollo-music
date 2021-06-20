package com.tremollo.api.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "\"comments\"")
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer commentId;

    @Column(name = "content_id")
    Integer contentId;

    @Column(name = "user_id")
    Integer userId;

    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    Date dateCreated;

    @Column(name = "comment_text")
    String text;

    public Comment(){

    }

    public Comment(Integer commentId, Integer contentId, Integer userId, Date dateCreated, String text) {
        this.commentId = commentId;
        this.contentId = contentId;
        this.userId = userId;
        this.dateCreated = dateCreated;
        this.text = text;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
