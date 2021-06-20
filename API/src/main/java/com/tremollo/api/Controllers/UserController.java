package com.tremollo.api.Controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tremollo.api.Entity.Hybrid.Feed;
import com.tremollo.api.Entity.User;
import com.tremollo.api.Service.IContentService;
import com.tremollo.api.Service.IUserService;
import com.tremollo.api.Utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    IUserService userService;

    @Autowired
    IContentService contentService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/user/all")
    public Object getAllUsers(){
        List<User> userList = userService.getAll();
        return new ApiResponse(userList);
    }

    @GetMapping("/user")
    public Object getUser(@RequestParam Integer userId){
        ApiResponse response;
        try{
            User user = userService.get(userId);
            response = new ApiResponse(user);
        }
        catch(Exception e){
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @PostMapping("/user/create")
    public Object createUser(@RequestBody User user){
        ApiResponse response;
        try{
            user.setFollowersCount(0);
            User savedUser = userService.create(user);
            response = new ApiResponse(savedUser);
        }
        catch(Exception e){
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @PutMapping("/user/avatar")
    public Object uploadAvatar(@RequestParam("file") MultipartFile file, @RequestParam("userId") Integer userId){
        ApiResponse response;
        try{
            User user = userService.uploadAvatar(userId, file);
            response = new ApiResponse(user);
        }
        catch(Exception e){
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @PostMapping("/user/login")
    public Object loginUser(@RequestBody String request){
        ApiResponse response;

        try{
            JsonNode node = mapper.readTree(request);
            String email = node.get("email").asText();
            String password = node.get("password").asText();
            User user = userService.get(email,password);
            response = new ApiResponse(user);
        }
        catch(Exception e){
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @GetMapping("/feed")
    public Object getFeed(@RequestParam Integer pageNumber,@RequestParam Integer rowCount, @RequestParam Integer userId){
        ApiResponse response;
        try{
            List<Feed> feed = contentService.getFeed(pageNumber,rowCount,userId);
            response = new ApiResponse(feed);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @GetMapping("/user/stats")
    public Object getStats(@RequestParam("userId") Integer userId){
        ApiResponse response;
        try{
            response = new ApiResponse(userService.getstats(userId));
        }
        catch(Exception e){
            e.printStackTrace();
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @GetMapping("/user/content")
    public Object getUserContent(@RequestParam("userId") Integer userId){
        ApiResponse response;
        try{
            response = new ApiResponse(contentService.getUserContent(userId));
        }
        catch(Exception e){
            e.printStackTrace();
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @GetMapping("/user/search")
    public Object searchUser(@RequestParam("keyword") String keyword){
        ApiResponse response;
        try{
            response = new ApiResponse(userService.search(keyword));
        }
        catch(Exception e){
            e.printStackTrace();
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @GetMapping("/user/followers")
    public Object userFollowers(@RequestParam("userId") Integer userId){
        ApiResponse response;
        try{
            response = new ApiResponse(userService.getUserFollowers(userId));
        }
        catch(Exception e){
            e.printStackTrace();
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @GetMapping("/user/following")
    public Object userFollows(@RequestParam("userId") Integer userId){
        ApiResponse response;
        try{
            response = new ApiResponse(userService.getUserFollowing(userId));
        }
        catch(Exception e){
            e.printStackTrace();
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }
}
