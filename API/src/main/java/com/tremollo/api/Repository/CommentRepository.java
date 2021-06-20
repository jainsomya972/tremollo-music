package com.tremollo.api.Repository;

import com.tremollo.api.DTO.CommentDTO;
import com.tremollo.api.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query(value = "select t.*,username,avatar from (select * from comments where content_id = :contentId) as t, public.user \n" +
            "where t.user_id = public.user.user_id  order by date_created desc;", nativeQuery = true)
    List<CommentDTO> getCommentsOnContent(@Param("contentId") Integer contentId);
}
