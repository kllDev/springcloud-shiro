package com.kll.springcloud.service;

import com.kll.springcloud.entities.MallUser;
import com.kll.springcloud.entities.UserLogin;
import com.kll.springcloud.mapper.UserMapper;
import com.kll.springcloud.model.UserSignUp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-18 15:19
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public MallUser login(UserLogin userLogin) {
        List<MallUser> userEntities = userMapper.getUserEntities(userLogin.getUserName());
        if (userEntities.size() == 1) {
            return userEntities.get(0);
        }
        return null;
    }

    @Override
    public String signUp(UserSignUp userSignUp) {
        List<MallUser> userEntities = userMapper.getUserEntities(userSignUp.getUsername());
        if (userEntities.size() == 0) {
            int insert = userMapper.insert(UserSignUp.getEntityFormInfo(userSignUp));
            return String.valueOf(insert);
        }
        return null;
    }
}