package com.tremollo.api.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "\"content\"")
public class Content {

    @Column(name = "caption")
    String caption;

    @Column(name = "media_link")
    String mediaLink;

    @Column(name = "thumbnail_link")
    String thumbnailLink;

    @Column(name = "date_upload")
    @Temporal(TemporalType.TIMESTAMP)
    Date dateUpload;

    @Id
    @Column(name = "content_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer contentId;

    @Column(name = "user_id")
    Integer userId;

    @Column(name = "title")
    String title;

    @Column(name = "likes_count")
    Integer likesCount;

    @Column(name = "tags")
    String tags;

    public Content() {
    }

    public Content(String caption, String mediaLink, String thumbnailLink, Date dateUpload, Integer contentId, Integer userId, String title, Integer likesCount, String tags) {
        this.caption = caption;
        this.mediaLink = mediaLink;
        this.thumbnailLink = thumbnailLink;
        this.dateUpload = dateUpload;
        this.contentId = contentId;
        this.userId = userId;
        this.title = title;
        this.likesCount = likesCount;
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Content{" +
                "caption='" + caption + '\'' +
                ", mediaLink='" + mediaLink + '\'' +
                ", thumbnailLink='" + thumbnailLink + '\'' +
                ", dateUpload=" + dateUpload +
                ", contentId=" + contentId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", likesCount=" + likesCount +
                ", tags='" + tags + '\'' +
                '}';
    }

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public void setThumbnailLink(String thumbnailLink) {
        this.thumbnailLink = thumbnailLink;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getMediaLink() {
        return mediaLink;
    }

    public void setMediaLink(String mediaLink) {
        this.mediaLink = mediaLink;
    }

    public Date getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(Date dateUpload) {
        this.dateUpload = dateUpload;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
