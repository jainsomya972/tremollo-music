package com.tremollo.api.Service;

import com.tremollo.api.DTO.CommentDTO;
import com.tremollo.api.DTO.UserListDTO;
import com.tremollo.api.Entity.Comment;
import com.tremollo.api.Entity.Content;
import com.tremollo.api.Entity.FileTemp;
import com.tremollo.api.Entity.Hybrid.Feed;
import org.bytedeco.javacv.FrameGrabber;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IContentService {

    List<Feed> getFeed(Integer pageNo, Integer rowCount, Integer userId);
    FileTemp uploadFile(MultipartFile multipartFile) throws FrameGrabber.Exception;
    Content uploadContent(Content content, Integer fileId);
    Integer getLikesCount(Integer contentId);
    List<Content> getUserContent(Integer userId);
    List<Content> getPlaylistContent(List<Integer> contentId);
    List<Content> search(String keyword);
    Feed getContentWithUserById(Integer contentId, Integer userId);
    List<UserListDTO> getContentLikesList(Integer contentId);

    Comment createCommentOnContent(Comment comment);
    List<CommentDTO> getCommentsOnContent(Integer contentId);
}
