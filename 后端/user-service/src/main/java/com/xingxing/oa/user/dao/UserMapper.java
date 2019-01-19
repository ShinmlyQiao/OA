package com.xingxing.oa.user.dao;


import com.xingxing.oa.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User user);

    int insertSelective(User user);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User user);

    int updateByPrimaryKey(User user);
}