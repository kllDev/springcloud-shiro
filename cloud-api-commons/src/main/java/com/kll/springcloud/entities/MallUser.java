package com.kll.springcloud.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-18 15:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class MallUser implements Serializable {
    @TableId(type= IdType.AUTO)
    private Integer id;

    private String userName;

    private String realName;

    private String password;

    private String salt;

    private String phoneNum;

    private String mail;
}