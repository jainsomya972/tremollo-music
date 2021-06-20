package com.tremollo.api.Service;

import com.tremollo.api.DTO.UserListDTO;
import com.tremollo.api.Entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface IUserService {

    List<User> getAll();

    User create(User user);

    User create(User user, MultipartFile file);

    User uploadAvatar(Integer userId, MultipartFile file);

    User get(String email, String password);

    User get(Integer userId);

    HashMap<String, Integer> getstats(Integer userId);

    List<User> search(String keyword);

    public List<UserListDTO> getUserFollowers(Integer userId);

    public List<UserListDTO> getUserFollowing(Integer userId);

}
