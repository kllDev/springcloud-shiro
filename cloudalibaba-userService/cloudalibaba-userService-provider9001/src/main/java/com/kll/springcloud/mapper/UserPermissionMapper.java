package com.kll.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kll.springcloud.entities.Permission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserPermissionMapper extends BaseMapper<Permission> {
    /**
     * 根据用户查询用户权限
     */
    @Select("select * from  role where role_code in(select role_code from user_role where username = #{username})")
    public List<Permission> queryByUser(@Param("username") String username);
}
