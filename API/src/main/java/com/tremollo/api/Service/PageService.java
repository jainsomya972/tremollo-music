package com.tremollo.api.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.tremollo.api.DTO.PageDTO;
import com.tremollo.api.Entity.Page;
import com.tremollo.api.Entity.User;
import com.tremollo.api.Repository.PageRepository;
import com.tremollo.api.Utils.S3Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class PageService implements IPageService {

    @Autowired
    PageRepository pageRepository;

    @Autowired
    S3Helper s3Helper;

    @Override
    public PageDTO createNewPage(String name, String about, Integer userId) {
        Page page = new Page();
        page.setName(name);
        page.setAbout(about);
        page.setUserId(userId);
        Page savedPage = pageRepository.save(page);
        return new PageDTO(savedPage);
    }

    @Override
    public PageDTO uploadAvatar(Integer pageId, MultipartFile mpfile) {
        File file = s3Helper.convertMultiPartFileToFile(mpfile);
        Page page = pageRepository.findById(pageId).get();
        if(page.getAvatar()!=null)
            s3Helper.delete(page.getAvatar(),s3Helper.getBucketName());
        String fileName = "Page/Avatar/"+ System.currentTimeMillis() +"_"+mpfile.getOriginalFilename();
        s3Helper.upload(fileName,file,s3Helper.getBucketName());
        page.setAvatar(fileName);
        Page savedPage = pageRepository.save(page);
        return new PageDTO(savedPage);
    }
}
