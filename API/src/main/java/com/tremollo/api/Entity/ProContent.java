package com.tremollo.api.Entity;

import com.tremollo.api.Enums.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "\"pro_content\"")
public class ProContent {

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

    @Column(name = "play_count")
    Integer playCount;

    @Column(name = "tags")
    String tags;

    @Column(name = "language")
    Language language;

    @Column(name = "genre")
    Genre genre;

    @Column(name = "instrument")
    Instrument instrument;

    @Column(name = "mood")
    Mood mood;

    @Column(name = "approve_status")
    ApproveStatus approveStatus;

    @Column(name = "region")
    String region;

    @Column(name = "likes_current_month_count")
    Integer likesCountCurrentMonth;

    @Column(name = "plays_current_month_count")
    Integer playsCountCurrentMonth;

    public ProContent() {

    }

    public ProContent(String caption, String mediaLink, String thumbnailLink, Date dateUpload, Integer contentId, Integer userId, String title, Integer likesCount, Integer playCount, String tags, Language language, Genre genre, Instrument instrument, Mood mood, ApproveStatus approveStatus, String region, Integer likesCountCurrentMonth, Integer playsCountCurrentMonth) {
        this.caption = caption;
        this.mediaLink = mediaLink;
        this.thumbnailLink = thumbnailLink;
        this.dateUpload = dateUpload;
        this.contentId = contentId;
        this.userId = userId;
        this.title = title;
        this.likesCount = likesCount;
        this.playCount = playCount;
        this.tags = tags;
        this.language = language;
        this.genre = genre;
        this.instrument = instrument;
        this.mood = mood;
        this.approveStatus = approveStatus;
        this.region = region;
        this.likesCountCurrentMonth = likesCountCurrentMonth;
        this.playsCountCurrentMonth = playsCountCurrentMonth;
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

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public void setThumbnailLink(String thumbnailLink) {
        this.thumbnailLink = thumbnailLink;
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

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Integer getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Integer playCount) {
        this.playCount = playCount;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public ApproveStatus getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(ApproveStatus approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getLikesCountCurrentMonth() {
        return likesCountCurrentMonth;
    }

    public void setLikesCountCurrentMonth(Integer likesCountCurrentMonth) {
        this.likesCountCurrentMonth = likesCountCurrentMonth;
    }

    public Integer getPlaysCountCurrentMonth() {
        return playsCountCurrentMonth;
    }

    public void setPlaysCountCurrentMonth(Integer playsCountCurrentMonth) {
        this.playsCountCurrentMonth = playsCountCurrentMonth;
    }

    @Override
    public String toString() {
        return "ProContent{" +
                "caption='" + caption + '\'' +
                ", mediaLink='" + mediaLink + '\'' +
                ", thumbnailLink='" + thumbnailLink + '\'' +
                ", dateUpload=" + dateUpload +
                ", contentId=" + contentId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", likesCount=" + likesCount +
                ", playCount=" + playCount +
                ", tags='" + tags + '\'' +
                ", language=" + language +
                ", genre=" + genre +
                ", instrument=" + instrument +
                ", mood=" + mood +
                ", approveStatus=" + approveStatus +
                ", region='" + region + '\'' +
                ", likesCountCurrentMonth=" + likesCountCurrentMonth +
                ", playsCountCurrentMonth=" + playsCountCurrentMonth +
                '}';
    }
}
