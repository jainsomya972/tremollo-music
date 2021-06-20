package com.tremollo.api.Service;

import com.tremollo.api.Entity.FileTemp;
import com.tremollo.api.Repository.FileTempRepository;
import com.tremollo.api.Utils.S3Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private QueryService queryService;

    @Autowired
    private FileTempRepository fileTempRepo;

    @Autowired
    private S3Helper s3Helper;

    @Scheduled(fixedDelay = 100000) // 100 sec delay
    public void pingDatabase(){
        queryService.ping().getResultList();
    }

    @Scheduled(fixedRate = 1800000) // every 30 min
    public List<String> cleanVM(){
        File folder = new File(System.getProperty("user.dir"));
        List<String> deletedFiles = new ArrayList<>();
        for(File item : folder.listFiles()){
            if(item.getName().contains("mp4")||item.getName().contains("png")||
                    item.getName().contains("mkv")||item.getName().contains("jpg")){
                if(System.currentTimeMillis() - item.lastModified() > 900000L)
                    if(item.delete())
                        deletedFiles.add(item.getName());
            }
        }
        return deletedFiles;
    }

//    @Scheduled(fixedRate = 1800000) //every 30 min
    public List<String> cleanS3(){
        List<FileTemp> rows = fileTempRepo.findAll();
        List<String> deletedFiles = new ArrayList<>();
        for(FileTemp item : rows){
            //if more than 30 mins old
            if(System.currentTimeMillis() - item.getDateUpload().getTime() > 900000L) {
                String mediaLink = item.getFilePath();
                deletedFiles.add(mediaLink);
                String[] thumbnailLinks = item.getFileThumbnails().split(",");
                s3Helper.delete(mediaLink, s3Helper.getBucketName());

                for (String link : thumbnailLinks) {
                    s3Helper.delete(link, s3Helper.getBucketName());
                    deletedFiles.add(link);
                }
                fileTempRepo.deleteById(item.getFileId());
            }
        }
        return deletedFiles;
    }
}
