package com.tremollo.api.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.tremollo.api.DTO.UserListDTO;
import com.tremollo.api.Entity.User;
import com.tremollo.api.Repository.ContentRepository;
import com.tremollo.api.Repository.FollowsRepository;
import com.tremollo.api.Repository.LikesRepository;
import com.tremollo.api.Repository.UserRepository;
import com.tremollo.api.Utils.S3Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    ContentRepository contentRepo;

    @Autowired
    LikesRepository likesRepo;

    @Autowired
    FollowsRepository followsRepo;

    @Autowired
    S3Helper s3Helper;

    @Autowired
    QueryService queryService;

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public User create(User user){
        user.setDateCreated(new Date());
        return userRepo.save(user);
    }

    @Override
    public User create(User user, MultipartFile mpfile){
        File file = s3Helper.convertMultiPartFileToFile(mpfile);
        String fileName = "User/Avatar/"+ System.currentTimeMillis() +"_"+mpfile.getOriginalFilename();
        s3Helper.upload(fileName,file,s3Helper.getBucketName());
        user.setAvatarLink(fileName);
        return create(user);
    }

    @Override
    public User uploadAvatar(Integer userId, MultipartFile mpfile){
        File file = s3Helper.convertMultiPartFileToFile(mpfile);
        User user = userRepo.findById(userId).get();
        if(user.getAvatarLink()!=null)
            s3Helper.delete(user.getAvatarLink(),s3Helper.getBucketName());
        String fileName = "User/Avatar/"+ System.currentTimeMillis() +"_"+mpfile.getOriginalFilename();
        s3Helper.upload(fileName,file,s3Helper.getBucketName());
        user.setAvatarLink(fileName);
        return userRepo.save(user);
    }

    @Override
    public User get(String email,String password){
        return userRepo.findByEmailAndPassword(email,password);
    }

    @Override
    public User get(Integer userId) {
        return userRepo.findById(userId).get();
    }

    @Override
    public HashMap<String, Integer> getstats(Integer userId) {
        HashMap<String,Integer> map = new HashMap<>();
        map.put("following",followsRepo.countByFollowerId(userId));
        map.put("followers",followsRepo.countByFollowedId(userId));
        map.put("uploads",contentRepo.countByUserId(userId));
        return map;
    }

    @Override
    public List<User> search(String keyword) {
        List<User> userList = userRepo.searchByUsernameOrLnameOrFname("%"+keyword+"%");
//        List<User> userList = queryService.userSearchQuery(keyword).getResultList();
        return userList;
    }

    public List<UserListDTO> getUserFollowers(Integer userId){
        return  userRepo.getFollowers(userId);
    }

    public List<UserListDTO> getUserFollowing(Integer userId){
        return  userRepo.getFollowing(userId);
    }
}
