package com.xingxing.oa.interfaces;

public interface BaseMapper<T> {
    int deleteByPrimaryKey(T id);

    int insert(T user);

    int insertSelective(T user);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T user);

    int updateByPrimaryKey(T user);
}
