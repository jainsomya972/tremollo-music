package com.tremollo.api.DTO;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public interface UserListDTO {

    @Value("#{target.fname}")
    String getFirstName();

    @Value("#{target.lname}")
    String getLastName();

    @Value("#{target.username}")
    String getUsername();

    @Value("#{target.avatar}")
    String getAvatarLink();

    @Value("#{target.user_id}")
    Integer getUserId();
}


