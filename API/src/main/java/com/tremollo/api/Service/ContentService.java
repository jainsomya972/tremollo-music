package com.tremollo.api.Service;

import com.tremollo.api.DTO.CommentDTO;
import com.tremollo.api.DTO.UserListDTO;
import com.tremollo.api.Entity.Comment;
import com.tremollo.api.Entity.Content;
import com.tremollo.api.Entity.FileTemp;
import com.tremollo.api.Entity.Hybrid.Feed;
import com.tremollo.api.Repository.*;
import com.tremollo.api.Utils.S3Helper;
import com.tremollo.api.Utils.VideoHelper;
import org.bytedeco.javacv.FrameGrabber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Query;
import java.io.File;
import java.util.Date;
import java.util.List;

@Service
public class ContentService implements  IContentService{

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    QueryService queryService;

    @Autowired
    LikesRepository likesRepository;

    @Autowired
    FollowsRepository followsRepository;

    @Autowired
    FileTempRepository fileTempRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    S3Helper s3Helper;

    @Override
    public List<Feed> getFeed(Integer pageNo, Integer rowCount, Integer userId) {
        Query q = queryService.feedQuery((pageNo-1)*rowCount,rowCount);
        List<Feed> feedList = q.getResultList();
        for(Feed feed : feedList){
//            int likes = likesRepository.countByContentId(feed.getContentId());
//            int follows = followsRepository.countByFollowedId(feed.getUserId());
            boolean isLiked = likesRepository.countByUserIdAndContentId(userId,feed.getContentId())>0;
            boolean isFollowed = followsRepository.countByFollowerIdAndFollowedId(userId, feed.getUserId())>0;
//            feed.setLikes(likes);
//            feed.setFollowers(follows);
            feed.setIsLikedByUser(isLiked);
            feed.setIsFollowedByUser(isFollowed);
        }
        return feedList;
    }

    @Override
    public FileTemp uploadFile(MultipartFile multipartFile) throws FrameGrabber.Exception {
        File file =  s3Helper.convertMultiPartFileToFile(multipartFile);
        Long currTime = System.currentTimeMillis();
        String fileName = "uploads/"+currTime+"_"+multipartFile.getOriginalFilename();

        //uploading main file
        s3Helper.upload(fileName,file,s3Helper.getBucketName());

        //extracting frames
        List<File> frames  = VideoHelper.grabFrames(file,4);

        //uploading frames
        String thumbnails = "";
        for(File frame : frames){
            String frameName = "uploads/thumbnails/"+currTime+"_"+frame.getName();
            s3Helper.upload(frameName,frame,s3Helper.getBucketName());
            thumbnails += frameName+",";
        }

        //saving file metadata to table
        FileTemp fileTemp = new FileTemp();
        fileTemp.setFilePath(fileName);
        fileTemp.setDateUpload(new Date(currTime));
        fileTemp.setFileThumbnails(thumbnails);
        return fileTempRepository.save(fileTemp);
    }

    public Content uploadContent(Content content, Integer fileId){
        FileTemp fileTemp = fileTempRepository.findById(fileId).get();
        content.setMediaLink(fileTemp.getFilePath());
        content.setDateUpload(fileTemp.getDateUpload());
        content.setLikesCount(0);

        content = contentRepository.save(content);

        //deleting unwanted thumbnails
        String[] thumbnails = fileTemp.getFileThumbnails().split(",");
        for(String thumbnail : thumbnails){
            if(!thumbnail.equals("") && !thumbnail.equals(content.getThumbnailLink()))
                s3Helper.delete(thumbnail,s3Helper.getBucketName());
        }

        fileTempRepository.delete(fileTemp);
        return content;
    }

    @Override
    public Integer getLikesCount(Integer contentId) {
        return likesRepository.countByContentId(contentId);
    }

    @Override
    public List<Content> getUserContent(Integer userId){
        return contentRepository.findAllByUserId(userId);
    }

    @Override
    public List<Content> getPlaylistContent(List<Integer> contentIds) {
        return contentRepository.findAllByContentIds(contentIds);
    }

    @Override
    public List<Content> search(String keyword) {
        return contentRepository.searchByTitleOrCaptionOrTags("%"+keyword+"%");
    }

    @Override
    public Feed getContentWithUserById(Integer contentId, Integer userId) {
        List<Feed> result = queryService.getContentWithUserQuery(contentId).getResultList();
        Feed item = result.get(0);
        if(userId!=null) {
            item.setIsLikedByUser(likesRepository.countByUserIdAndContentId(userId, contentId) > 0);
            item.setIsFollowedByUser(followsRepository.countByFollowerIdAndFollowedId(userId, item.getUserId()) > 0);
        }
        return result.get(0);
    }

    @Override
    public List<UserListDTO> getContentLikesList(Integer contentId) {
        return likesRepository.findContentLikesUsers(contentId);
    }

    @Override
    public Comment createCommentOnContent(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<CommentDTO> getCommentsOnContent(Integer contentId) {
        return commentRepository.getCommentsOnContent(contentId);
    }
}
