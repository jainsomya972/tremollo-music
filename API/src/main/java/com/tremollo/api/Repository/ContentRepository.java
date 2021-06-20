package com.tremollo.api.Repository;

import com.tremollo.api.Entity.Content;
import com.tremollo.api.Entity.Hybrid.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content,Integer> {
    Integer countByUserId(Integer userId);
    List<Content> findAllByUserId(Integer userId);

    @Query(value = "select * from content where content_id in (:ids)", nativeQuery = true)
    List<Content> findAllByContentIds(@Param("ids") List<Integer> contentId);

    @Query(value = "select * from content where (title ilike :keyword ) or (caption ilike :keyword ) or (tags ilike :keyword )", nativeQuery = true)
    List<Content> searchByTitleOrCaptionOrTags(String keyword);
}
