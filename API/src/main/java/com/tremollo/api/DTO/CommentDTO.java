package com.tremollo.api.DTO;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Date;

public interface CommentDTO {

    @Value("#{target.comment_id}")
    Integer getCommentId();

    @Value("#{target.content_id}")
    Integer getContentId();

    @Value("#{target.user_id}")
    Integer getUserId();

    @Value("#{target.date_created}")
    Date getDateCreated();

    @Value("#{target.comment_text}")
    String getText();

    @Value("#{target.username}")
    String getUsername();

    @Value("#{target.avatar}")
    String getAvatarLink();

}
