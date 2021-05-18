package com.kll.springcloud.service;

import com.kll.springcloud.entities.MallUser;
import com.kll.springcloud.entities.UserLogin;
import com.kll.springcloud.model.UserSignUp;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-18 15:19
 */
public interface UserService {

    MallUser login(UserLogin userLogin);

    String signUp(UserSignUp userSignUp);

}