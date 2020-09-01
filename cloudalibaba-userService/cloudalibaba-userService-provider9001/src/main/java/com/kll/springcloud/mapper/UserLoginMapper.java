package com.kll.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kll.springcloud.entities.UserLogin;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface UserLoginMapper extends BaseMapper<UserLogin> {

    /**
     * 根据用户查询用户权限
     */
    @Select("select * from sys_user where user_name = #{username} and del_flag = '0'")
    public UserLogin getUserByAccount(@Param("username") String username);


}
