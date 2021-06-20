package com.tremollo.api.Entity.Hybrid;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "\"content_user_combined\"")
public class Feed {

    @Column(name = "caption")
    String caption;

    @Column(name = "title")
    String title;

    @Column(name = "media_link")
    String mediaLink;

    @Column(name = "thumbnail_link")
    String thumbnailLink;

    @Column(name = "date_upload")
    @Temporal(TemporalType.TIMESTAMP)
    Date dateUpload;

    @Id
    @Column(name = "content_id")
    Integer contentId;

    @Column(name = "user_id")
    Integer userId;

    @Column(name = "username")
    String username;

    @Column(name = "avatar")
    String pageAvatar;

    @Column(name = "likes_count")
    Integer likes;

    @Column(name = "followers_count")
    Integer followers;

    @Transient
    boolean isLikedByUser;

    @Transient
    boolean isFollowedByUser;

    public Feed(String caption, String mediaLink, String thumbnailLink, Date dateUpload, Integer contentId, Integer pageId, String username, String pageAvatar) {
        this.caption = caption;
        this.mediaLink = mediaLink;
        this.thumbnailLink = thumbnailLink;
        this.dateUpload = dateUpload;
        this.contentId = contentId;
        this.userId = pageId;
        this.username = username;
        this.pageAvatar = pageAvatar;

        this.likes = 0;
        this.followers = 0;
        this.isLikedByUser = false;
        this.isFollowedByUser = false;
    }

    public Feed() {
        this.likes = 0;
        this.followers = 0;
        this.isLikedByUser = false;
        this.isFollowedByUser = false;
    }

    public Feed(String caption, String title, String mediaLink, String thumbnailLink, Date dateUpload, Integer contentId, Integer userId, String username, String pageAvatar, Integer likes, Integer followers, boolean isLikedByUser, boolean isFollowedByUser) {
        this.caption = caption;
        this.title = title;
        this.mediaLink = mediaLink;
        this.thumbnailLink = thumbnailLink;
        this.dateUpload = dateUpload;
        this.contentId = contentId;
        this.userId = userId;
        this.username = username;
        this.pageAvatar = pageAvatar;
        this.likes = likes;
        this.followers = followers;
        this.isLikedByUser = isLikedByUser;
        this.isFollowedByUser = isFollowedByUser;
    }

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public void setThumbnailLink(String thumbnailLink) {
        this.thumbnailLink = thumbnailLink;
    }

    public boolean isLikedByUser() {
        return isLikedByUser;
    }

    public void setLikedByUser(boolean likedByUser) {
        isLikedByUser = likedByUser;
    }

    public boolean isFollowedByUser() {
        return isFollowedByUser;
    }

    public void setFollowedByUser(boolean followedByUser) {
        isFollowedByUser = followedByUser;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPageAvatar() {
        return pageAvatar;
    }

    public void setPageAvatar(String pageAvatar) {
        this.pageAvatar = pageAvatar;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Boolean getIsLikedByUser() {
        return isLikedByUser;
    }

    public void setIsLikedByUser(Boolean isLikedByUser) {
        this.isLikedByUser = isLikedByUser;
    }

    public Boolean getIsFollowedByUser() {
        return isFollowedByUser;
    }

    public void setIsFollowedByUser(Boolean isFollowedByUser) {
        this.isFollowedByUser = isFollowedByUser;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "caption='" + caption + '\'' +
                ", title='" + title + '\'' +
                ", mediaLink='" + mediaLink + '\'' +
                ", thumbnailLink='" + thumbnailLink + '\'' +
                ", dateUpload=" + dateUpload +
                ", contentId=" + contentId +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", pageAvatar='" + pageAvatar + '\'' +
                ", likes=" + likes +
                ", followers=" + followers +
                ", isLikedByUser=" + isLikedByUser +
                ", isFollowedByUser=" + isFollowedByUser +
                '}';
    }
}
