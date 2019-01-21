package com.xingxing.oa.user.dao;


import com.xingxing.oa.interfaces.BaseMapper;
import com.xingxing.oa.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}