package com.tremollo.api.DTO;

import com.tremollo.api.Entity.Content;
import com.tremollo.api.Entity.Playlist;
import com.tremollo.api.Service.IContentService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlaylistDTO {

    String name;
    Integer playlistId;
    Date dateCreated;
    List<Content> contents;
    Integer userId;

    public PlaylistDTO(String name, Integer playlistId, Date dateCreated, ArrayList<Content> contents, Integer userId) {
        this.name = name;
        this.playlistId = playlistId;
        this.dateCreated = dateCreated;
        this.contents = contents;
        this.userId = userId;
    }

    public PlaylistDTO(){};

    public PlaylistDTO(Playlist playlist, List<Content> contents){
        name = playlist.getName();
        playlistId = playlist.getPlaylistId();
        dateCreated = playlist.getDateCreated();
        userId = playlist.getUserId();
        this.contents = contents;
    }

//    public Playlist getPlaylist(){
//        Playlist playlist = new Playlist();
//        playlist = playlist.setName(name)
//                .setDateCreated(dateCreated)
//                .setPlaylistId(playlistId)
//                .setUserId(userId);
//        StringBuilder contentString = new StringBuilder("");
//        if(!contents.isEmpty())
//            contentString.append(contents.get(0));
//        for(int i=1;i<contents.size();i++)
//            contentString.append(",").append(contents.get(0));
//        return playlist.setContents(contentString.toString());
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Integer playlistId) {
        this.playlistId = playlistId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PlaylistDTO{" +
                "name='" + name + '\'' +
                ", playlistId='" + playlistId + '\'' +
                ", dateCreated=" + dateCreated +
                ", contents=" + contents +
                ", userId=" + userId +
                '}';
    }

}
