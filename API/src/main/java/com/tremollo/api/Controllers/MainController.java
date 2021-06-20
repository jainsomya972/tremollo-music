package com.tremollo.api.Controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tremollo.api.DTO.PageDTO;
import com.tremollo.api.DTO.PlaylistDTO;
import com.tremollo.api.Entity.*;
import com.tremollo.api.Entity.Hybrid.Feed;
import com.tremollo.api.Repository.FollowsRepository;
import com.tremollo.api.Repository.LikesRepository;
import com.tremollo.api.Service.IContentService;
import com.tremollo.api.Service.IPageService;
import com.tremollo.api.Service.IPlaylistService;
import com.tremollo.api.Service.IUserService;
import com.tremollo.api.Utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class MainController {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    IUserService userService;

    @Autowired
    IContentService contentService;

    @Autowired
    IPlaylistService playlistService;

    @Autowired
    IPageService pageService;

    @Autowired
    LikesRepository likesRepository;

    @Autowired
    FollowsRepository followsRepository;

    @GetMapping("/")
    public String index(){
        return  "<html>\n" +
                "  <head>\n" +
                "    <style>\n" +
                "      td{margin-left: 50px; padding-left: 50px; font-family: monospace; font-size: large;}\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <h2>tremollo.co REST API</h2>\n" +
                "    <br><br><br>\n" +
                "    <table>\n" +
                "      <tr><td>Method    </td><td>Endpoint</td></tr>\n" +
                "      <tr><td><br></td><td></td></tr>\n" +
                "      <tr><td>GET     </td><td>/</td></tr>\n" +
                "      <tr><td>GET     </td><td>/user/all</td></tr>\n" +
                "      <tr><td>PUT     </td><td>/user/avatar</td></tr>\n" +
                "      <tr><td>GET     </td><td>/user/playlist</td></tr>\n" +
                "      <tr><td>POST    </td><td>/user/create</td></tr>\n" +
                "      <tr><td>POST    </td><td>/user/login</td></tr>\n" +
                "      <tr><td>GET    </td><td>/user/search</td></tr>\n" +
                "      <tr><td>PUT    </td><td>/user/follow</td></tr>\n" +
                "      <tr><td>GET     </td><td>/content/feed</td></tr>\n" +
                "      <tr><td>PUT    </td><td>/content/like</td></tr>\n" +
                "      <tr><td>GET    </td><td>/content/likes</td></tr>\n" +
                "      <tr><td>GET    </td><td>/content/withUser</td></tr>\n" +
                "      <tr><td>POST    </td><td>/content/comments</td></tr>\n" +
                "      <tr><td>GET    </td><td>/content/search</td></tr>\n" +
                "      <tr><td>POST    </td><td>/playlist/create</td></tr>\n" +
                "      <tr><td>POST    </td><td>/playlist/add</td></tr>\n" +
                "      <tr><td>DELETE    </td><td>/playlist</td></tr>\n" +
                "      <tr><td>POST    </td><td>/page/create</td></tr>\n" +
                "      <tr><td>PUT    </td><td>/page/avatar</td></tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>";
    }

    @PostMapping("/playlist/add")
    public Object addContentToPlaylist(@RequestBody String request){
        ApiResponse response;
        try{
            JsonNode node = mapper.readTree(request);
            Integer playlistId = node.get("playlistId").asInt();
            Integer contentId = node.get("contentId").asInt();
            PlaylistDTO dto = playlistService.addContentToPlaylist(playlistId,contentId);
            response = new ApiResponse(dto);
        }
        catch (Exception e){
            response = new ApiResponse(new int[0],e.toString());
        }
        return response;
    }

    @PostMapping("/playlist/create")
    public Object createPlaylist(@RequestBody String request){
        ApiResponse response;
        try{
            JsonNode node = mapper.readTree(request);
            String name = node.get("playlistName").asText();
            Integer userId = node.get("userId").asInt();
            PlaylistDTO savedPlaylist = playlistService.createNewPlaylist(name,userId);
            response = new ApiResponse(savedPlaylist);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @DeleteMapping("/playlist")
    public Object deletePlaylist(@RequestParam Integer playlistId){
        ApiResponse response;
        try{
            playlistService.deletePlaylist(playlistId);
            response = new ApiResponse(true);
        }
        catch(Exception e){
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @GetMapping("/user/playlist")
    public Object getUserPlaylists(@RequestParam Integer userId){
        ApiResponse response;
        try{
            List<PlaylistDTO> playlists = playlistService.getUserPlaylists(userId);
            response = new ApiResponse(playlists);
        }
        catch(Exception e){
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @PostMapping("/page/create")
    public Object createPage(@RequestBody String request){
        ApiResponse response;
        try{
            JsonNode node = mapper.readTree(request);
            String name = node.get("pageName").asText();
            String about = node.get("description").asText();
            Integer userId = node.get("userId").asInt();
            PageDTO page = pageService.createNewPage(name,about,userId);
            response = new ApiResponse(page);
        }
        catch(Exception e){
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @PutMapping("/page/avatar")
    public Object uploadPageAvatar(@RequestParam("file") MultipartFile file, @RequestParam("pageId") Integer pageId){
        ApiResponse response;
        try{
            User user = userService.uploadAvatar(pageId, file);
            response = new ApiResponse(user);
        }
        catch(Exception e){
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @PutMapping("content/upload/file")
    public Object uploadFile(@RequestParam("file") MultipartFile file){
        ApiResponse response;
        try{
            FileTemp fileTemp = contentService.uploadFile(file);
            response = new ApiResponse(fileTemp);
        }
        catch(Exception e){
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @PostMapping("content/upload/data")
    public Object uploadContentData(@RequestBody String request){
        ApiResponse response;
        try{
            JsonNode node = mapper.readTree(request);
            Content content = new Content();
            if(node.has("title")) content.setTitle(node.get("title").asText());
            if(node.has("caption")) content.setCaption(node.get("caption").asText());
            if(node.has("tags")) content.setTags(node.get("tags").asText());
            content.setUserId(node.get("userId").asInt());
            content.setThumbnailLink(node.get("thumbnailLink").asText());
            int fileId = node.get("fileId").asInt();
            content = contentService.uploadContent(content, fileId);
            response = new ApiResponse(content);
        }
        catch(Exception e){
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @PutMapping("/content/like")
    public Object likeContent(@RequestBody String request){
        ApiResponse response;
        try {
            JsonNode node = mapper.readTree(request);
            Integer userId = node.get("userId").asInt();
            Integer contentId = node.get("contentId").asInt();
            Boolean like = node.get("like").asBoolean(true);
            Likes likes = new Likes(userId, contentId);
            HashMap<String,Boolean> map = new HashMap<>();
            if (like){
                likesRepository.save(likes);
                map.put("liked",true);
            }
            else{
                likesRepository.delete(likes);
                map.put("liked",false);
            }
            response = new ApiResponse(map);
        }
        catch(Exception e){
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @GetMapping("/content")
    public Object getContents(@RequestParam("contentId") List<Integer> contentIds){
        ApiResponse response;
        try{
            response = new ApiResponse(contentService.getPlaylistContent(contentIds));
        }
        catch(Exception e){
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @GetMapping("/content/search")
    public Object searchContent(@RequestParam("keyword") String keyword){
        ApiResponse response;
        try{
            response = new ApiResponse(contentService.search(keyword));
        }
        catch(Exception e){
            e.printStackTrace();
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @GetMapping("/content/withUser")
    public Object getContentWithUser(@RequestParam Integer contentId,
                                     @RequestParam(required = false) Integer userId){
        ApiResponse response;
        try{
            response = new ApiResponse(contentService.getContentWithUserById(contentId, userId));
        }
        catch(Exception e){
            e.printStackTrace();
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @GetMapping("/content/likes")
    public Object getContentLikesList(@RequestParam("contentId") Integer contentId){
        ApiResponse response;
        try{
            response = new ApiResponse(contentService.getContentLikesList(contentId));
        }
        catch(Exception e){
            e.printStackTrace();
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @PostMapping("/content/comments")
    public Object createCommentOnContent(@RequestBody Comment comment){
        ApiResponse response;
        try{
            if(comment.getDateCreated()==null) comment.setDateCreated(new Date());
            response = new ApiResponse(contentService.createCommentOnContent(comment));
        }
        catch(Exception e){
            e.printStackTrace();
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @GetMapping("/content/comments")
    public Object getCommentsOnContent(@RequestParam("contentId") Integer contentId){
        ApiResponse response;
        try{
            response = new ApiResponse(contentService.getCommentsOnContent(contentId));
        }
        catch(Exception e){
            e.printStackTrace();
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }

    @PutMapping("/user/follow")
    public Object followPage(@RequestBody String request){
        ApiResponse response;
        try {
            JsonNode node = mapper.readTree(request);
            Integer followerId = node.get("followerId").asInt();
            Integer followedId = node.get("followedId").asInt();
            Boolean follow = node.get("follow").asBoolean(true);
            Follows follows = new Follows(followerId,followedId);
            HashMap<String,Boolean> map = new HashMap<>();
            if (follow){
                followsRepository.save(follows);
                map.put("followed",true);
            }
            else{
                followsRepository.delete(follows);
                map.put("followed",false);
            }
            response = new ApiResponse(map);
        }
        catch(Exception e){
            response = new ApiResponse(null,e.toString());
        }
        return response;
    }
}