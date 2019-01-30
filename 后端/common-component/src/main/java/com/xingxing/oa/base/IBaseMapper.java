package com.xingxing.oa.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IBaseMapper<T> {
    int deleteByPrimaryKey(Long id);

    int insert(T t);

    int insertSelective(T t);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T t);

    int updateByPrimaryKey(T t);

    List<T> selectByIds(@Param("ids") List<Long> ids);
}
