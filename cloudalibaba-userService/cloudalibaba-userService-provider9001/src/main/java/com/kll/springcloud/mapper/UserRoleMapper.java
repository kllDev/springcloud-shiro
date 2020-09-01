package com.kll.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kll.springcloud.entities.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserRoleMapper extends BaseMapper<Role> {

    @Select("select id from sys_role where id in (select role_id from sys_user_role where user_id = (select id from sys_user where user_name=#{username}))")
    List<String> getRoleByUserName(@Param("username") String username);
}
