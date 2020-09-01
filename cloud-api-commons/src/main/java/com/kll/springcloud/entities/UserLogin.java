package com.kll.springcloud.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private String id;

    /**
     * 登录账号
     */
    private String userName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * md5密码盐
     */
    private String salt;

    /**
     * 删除状态（0，正常，1已删除）
     */
    private String delFlag;
}
