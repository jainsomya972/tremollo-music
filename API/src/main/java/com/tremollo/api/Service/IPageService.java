package com.tremollo.api.Service;

import com.tremollo.api.DTO.PageDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IPageService {

    PageDTO createNewPage(String name,String about,Integer userId);
    PageDTO uploadAvatar(Integer pageId, MultipartFile file);
}
