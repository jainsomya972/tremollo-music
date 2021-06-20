package com.tremollo.api.Service;

import com.tremollo.api.DTO.UserListDTO;
import com.tremollo.api.Entity.Hybrid.Feed;
import com.tremollo.api.Entity.User;
import com.tremollo.api.Repository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Service
public class QueryService {

    @Autowired
    EntityManager em;

    Query feedQuery(Integer offset, Integer limit){
        Query q = em.createNativeQuery("select * from content_user_combined order by date_upload desc offset ?1 limit ?2", Feed.class);
        q.setParameter(1,offset);
        q.setParameter(2,limit);
        return q;
    }

    Query getContentWithUserQuery(Integer contentId){
        Query q = em.createNativeQuery("select * from (select * from content where content.content_id = ?1)" +
                " as c, \"user\" where c.user_id = \"user\".user_id;", Feed.class);
        q.setParameter(1,contentId);
        return q;
    }

    Query ping(){
        Query q = em.createNativeQuery("select 1");
        return q;
    }
}
