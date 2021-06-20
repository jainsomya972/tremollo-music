package com.tremollo.api.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "\"files_temp\"")
public class FileTemp {

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    Integer fileId;

    @Column(name = "file_path")
    String filePath;

    @Column(name = "date_upload")
    @Temporal(TemporalType.TIMESTAMP)
    Date dateUpload;

    @Column(name = "file_thumbnails")
    String fileThumbnails;

    public FileTemp(){}

    public FileTemp(Integer fileId, String filePath, Date dateUpload, String fileThumbnails) {
        this.fileId = fileId;
        this.filePath = filePath;
        this.dateUpload = dateUpload;
        this.fileThumbnails = fileThumbnails;
    }

    public String getFileThumbnails() {
        return fileThumbnails;
    }

    public void setFileThumbnails(String fileThumbnails) {
        this.fileThumbnails = fileThumbnails;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(Date dateUpload) {
        this.dateUpload = dateUpload;
    }

    @Override
    public String toString() {
        return "FileTemp{" +
                "fileId=" + fileId +
                ", filePath='" + filePath + '\'' +
                ", dateUpload=" + dateUpload +
                ", fileThumbnails='" + fileThumbnails + '\'' +
                '}';
    }
}
