package com.xingxing.oa.base;

import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

public abstract class BaseService<T> {

    private IBaseMapper<T> baseMapper;

    public BaseService(IBaseMapper<T> baseMapper){
        this.baseMapper = baseMapper;
    }

    /**
     * 创建对象前初始化值
     * @param t
     */
    public abstract void preAdd(T t);
    public abstract void postAdd(T t);
    public abstract void preUpdate(T t);
    public abstract void postUpdate(T t);
    public abstract void preDelete(Long t);
    public abstract void postDelete(Long t);

    public T getById(Long id){
        Objects.requireNonNull(id);
        return baseMapper.selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public int delById(Long id){
        Objects.requireNonNull(id);
        preDelete(id);
        int i = baseMapper.deleteByPrimaryKey(id);
        postDelete(id);
        return i;
    }

    @Transactional(rollbackFor = Exception.class)
    public int addOne(T t){
        Objects.requireNonNull(t);
        preAdd(t);
        int i = baseMapper.insertSelective(t);
        postAdd(t);
        return i;
    }

    /**
     * 更新null字段
     * @param t
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateById(T t){
        Objects.requireNonNull(t);
        preUpdate(t);
        int i = baseMapper.updateByPrimaryKey(t);
        postUpdate(t);
        return i;
    }

    /**
     * 不更新null字段
     * @param t
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateByIdSelective(T t){
        Objects.requireNonNull(t);
        preUpdate(t);
        int i = baseMapper.updateByPrimaryKeySelective(t);
        postUpdate(t);
        return i;
    }



}
