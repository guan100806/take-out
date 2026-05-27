package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

public interface UserService {
    /**
     * 微信登录
     *
     */
    public User wxLogin(UserLoginDTO userLoginDTO);
}
