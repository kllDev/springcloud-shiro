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
     * 密码
     */
    private String password;
}
