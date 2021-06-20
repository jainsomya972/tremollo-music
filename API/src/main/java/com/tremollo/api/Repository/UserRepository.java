package com.tremollo.api.Repository;

import com.tremollo.api.DTO.UserListDTO;
import com.tremollo.api.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@org.springframework.stereotype.Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    List<User> findAll();
    User findByEmailAndPassword(String email, String password);

    @Query(value="select * from public.user where (username ilike :name ) or (lname ilike :name ) \n" +
            "or (fname ilike :name );", nativeQuery = true)
    List<User> searchByUsernameOrLnameOrFname(@Param("name") String name);

    @Query(value="select followed_user_id as user_id, fname, lname, avatar, username from " +
            "(select * from ufollowsu where follower_user_id = :userId) as t, \"user\" where t.followed_user_id = user_id;", nativeQuery = true)
    List<UserListDTO> getFollowing(@Param("userId") Integer userId);

    @Query(value = "select follower_user_id as user_id, fname, lname, avatar, username from " +
            "(select * from ufollowsu where followed_user_id = :userId) as t, \"user\" where t.follower_user_id = user_id;", nativeQuery = true)
    List<UserListDTO> getFollowers(@Param("userId") Integer userId);
}
