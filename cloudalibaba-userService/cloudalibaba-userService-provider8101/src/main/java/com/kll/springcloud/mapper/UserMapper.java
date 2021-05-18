package com.kll.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kll.springcloud.entities.MallUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-04-18 15:22
 */
public interface UserMapper  extends BaseMapper<MallUser> {

    @Select("select * from user where user_name = #{userName} ")
    List<MallUser> getUserEntities(@Param("userName") String userName);

}