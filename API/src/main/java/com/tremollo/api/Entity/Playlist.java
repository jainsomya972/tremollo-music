package com.tremollo.api.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "\"playlist\"")
public class Playlist {

    @Column(name = "pname")
    String name;

    @Id
    @Column(name = "playlist_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer playlistId;

    @Column(name = "date_created")
    Date dateCreated;

    @Column(name = "contents")
    String contents;

    @Column(name = "user_id")
    Integer userId;

    public Playlist(String name, Integer playlistId, Date dateCreated, String contents, Integer userId) {
        this.name = name;
        this.playlistId = playlistId;
        this.dateCreated = dateCreated;
        this.contents = contents;
        this.userId = userId;
    }

    public Playlist() {
        this.dateCreated = new Date();
        this.contents = "";
    }

    public String getName() {
        return name;
    }

    public Playlist setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getPlaylistId() {
        return playlistId;
    }

    public Playlist setPlaylistId(Integer playlistId) {
        this.playlistId = playlistId;
        return this;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Playlist setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public String getContents() {
        return contents;
    }

    public Playlist setContents(String contents) {
        this.contents = contents;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Playlist setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "name='" + name + '\'' +
                ", playlistId='" + playlistId + '\'' +
                ", dateCreated=" + dateCreated +
                ", contents='" + contents + '\'' +
                ", userId=" + userId +
                '}';
    }
}
