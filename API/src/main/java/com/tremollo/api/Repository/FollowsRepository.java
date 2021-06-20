package com.tremollo.api.Repository;

import com.tremollo.api.Entity.Follows;
import com.tremollo.api.Entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowsRepository extends JpaRepository<Follows,Integer> {

    Integer countByFollowedId(Integer followedId);
    Integer countByFollowerId(Integer followerId);
    Integer countByFollowerIdAndFollowedId(Integer followerId, Integer followedId);

}
