package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    public static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties wx;
    @Autowired
    private UserMapper userMapper;

    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {

        // 调用微信服务，获取当前微信用户的openid
//        Map<String, String> map = new HashMap<>();
//        map.put("appid", wx.getAppid());
//        map.put("secret", wx.getSecret());
//        map.put("js_code", userLoginDTO.getCode());
//        map.put("grant_type", "authorization_code");
//
//        String openid = HttpClientUtil.doGet(WX_LOGIN_URL, map);
//        JSONObject jsonObject = JSON.parseObject(openid);
//        String openId = jsonObject.getString("openid");

        String openId = getOpenId(userLoginDTO.getCode());


        // 查询当前openid是否为空 ，为空就登录失败，抛出异常，否则就是合法的，需要判断此用户在数据库中是否存在，不存在就是新用户插入，存在就返回
        if(openId == null){
            throw new RuntimeException("登录失败");
        }else {
            User user = userMapper.getByOpenId(openId);
            if(user == null){
                // 新用户
                user = User.builder()
                        .openid(openId)
                        .createTime(LocalDateTime.now())
                        .build();
                userMapper.insertUser(user);
            }else {
                // 老用户
//                User user = User.builder()
//                        .openid(openId)
//                        .updateTime(LocalDateTime.now())
//                        .build();
//                userMapper.update(user);
            }
            return user;
        }
    }

    private String getOpenId(String code){
        Map<String, String> map = new HashMap<>();
        map.put("appid", wx.getAppid());
        map.put("secret", wx.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String openid = HttpClientUtil.doGet(WX_LOGIN_URL, map);
        JSONObject jsonObject = JSON.parseObject(openid);
        System.out.println(jsonObject);
        return jsonObject.getString("openid");
    }
}
