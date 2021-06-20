package com.tremollo.api.Repository;

import com.tremollo.api.DTO.UserListDTO;
import com.tremollo.api.Entity.Likes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Integer> {

    Integer countByContentId(Integer contentId);
    Integer countByUserIdAndContentId(Integer userId, Integer contentId);

    @Query(value = "select t.user_id, fname, lname, avatar, username \n" +
            "from (select * from likes where content_id = :contentId) as t, public.user \n" +
            "where t.user_id = public.user.user_id;", nativeQuery = true)
    List<UserListDTO> findContentLikesUsers(@Param("contentId") Integer contentId);
}
