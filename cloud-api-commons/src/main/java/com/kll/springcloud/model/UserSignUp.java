package com.kll.springcloud.model;

import com.kll.springcloud.entities.MallUser;
import lombok.Data;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-18 16:13
 */
@Data
public class UserSignUp {

    private String mail;

    private String username;

    private String phone;

    private String password;

    public static MallUser getEntityFormInfo(UserSignUp userSignUp) {
        MallUser mallUser = new MallUser();
        mallUser.setMail(userSignUp.getMail());
        mallUser.setUserName(userSignUp.getUsername());
        mallUser.setPassword(userSignUp.getPassword());
        mallUser.setPhoneNum(userSignUp.getPhone());
        return mallUser;
    }
}