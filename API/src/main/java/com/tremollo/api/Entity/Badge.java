package com.tremollo.api.Entity;

import javax.persistence.*;

@Entity
@Table(name = "\"badge\"")
public class Badge {

    @Id
    @Column(name = "badge_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer badgeId;

    @Column(name = "position")
    Integer position;

    @Column(name = "type")
    String type;

    @Column(name = "billboard_id")
    Integer billboardId;

    @Column(name = "content_id")
    Integer contentId;

    public Badge() {

    }

    public Badge(Integer badgeId, Integer position, String type, Integer billboardId, Integer contentId) {
        this.badgeId = badgeId;
        this.position = position;
        this.type = type;
        this.billboardId = billboardId;
        this.contentId = contentId;
    }

    public Integer getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(Integer badgeId) {
        this.badgeId = badgeId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getBillboardId() {
        return billboardId;
    }

    public void setBillboardId(Integer billboardId) {
        this.billboardId = billboardId;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    @Override
    public String toString() {
        return "Badge{" +
                "badgeId=" + badgeId +
                ", position=" + position +
                ", type='" + type + '\'' +
                ", billboardId=" + billboardId +
                ", contentId=" + contentId +
                '}';
    }
}
