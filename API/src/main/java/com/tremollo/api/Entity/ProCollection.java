package com.tremollo.api.Entity;

import com.tremollo.api.Enums.ProCollectionType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "\"pro_collections\"")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class ProCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pro_collection_id")
    Integer collectionId;

    @Column(name = "title")
    String title;

    @Column(name = "thumbnail_link")
    String thumbnailLink;

    @Column(name = "description")
    String description;

    @Column(name = "date_created")
    String dateCreated;

    @Column(name = "cover_link")
    String coverLink;

    @Column(name = "pro_contents")
    List<Integer> contents;

    @Column(name = "type")
    ProCollectionType type;

    @Column(name = "subtype")
    String subType;

    public ProCollection(){

    }

    public ProCollection(Integer collectionId, String title, String thumbnailLink, String description,
                         String dateCreated, String coverLink, List<Integer> contents, ProCollectionType type, String subType) {
        this.collectionId = collectionId;
        this.title = title;
        this.thumbnailLink = thumbnailLink;
        this.description = description;
        this.dateCreated = dateCreated;
        this.coverLink = coverLink;
        this.contents = contents;
        this.type = type;
        this.subType = subType;
    }

    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public void setThumbnailLink(String thumbnailLink) {
        this.thumbnailLink = thumbnailLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCoverLink() {
        return coverLink;
    }

    public void setCoverLink(String coverLink) {
        this.coverLink = coverLink;
    }

    public List<Integer> getContents() {
        return contents;
    }

    public void setContents(List<Integer> contents) {
        this.contents = contents;
    }

    public ProCollectionType getType() {
        return type;
    }

    public void setType(ProCollectionType type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }


    @Override
    public String toString() {
        return "ProCollection{" +
                "collectionId=" + collectionId +
                ", title='" + title + '\'' +
                ", thumbnailLink='" + thumbnailLink + '\'' +
                ", description='" + description + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", coverLink='" + coverLink + '\'' +
                ", contents=" + contents +
                ", type=" + type +
                ", subType='" + subType + '\'' +
                '}';
    }
}
