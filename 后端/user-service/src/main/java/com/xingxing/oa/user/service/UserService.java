package com.xingxing.oa.user.service;

import com.xingxing.oa.base.BaseService;
import com.xingxing.oa.base.IBaseMapper;
import com.xingxing.oa.redis.utils.RedisUtils;
import com.xingxing.oa.user.common.IdValueConstants;
import com.xingxing.oa.user.dao.UserMapper;
import com.xingxing.oa.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
public class UserService extends BaseService<User> {

    private UserMapper userMapper;

    @Autowired
    private RedisUtils redisUtils;


    public UserService(UserMapper userMapper) {
        super(userMapper);
        this.userMapper = userMapper;
    }


    /**
     * 设置添加时初始化值
     * id/createTime/updateTime/deleted/tenantId
     * 创建者/最后一次操作者
     * @param user
     */
    @Override
    public void preAdd(User user) {
        Long userId = redisUtils.getDomainId(IdValueConstants.USER_ID);
        user.setId(userId);
        user.init();
    }

    @Override
    public void postAdd(User user) {

    }

    /**
     * 设置更新时间，操作者
     * @param user
     */
    @Override
    public void preUpdate(User user) {
        user.setUpdateTime(LocalDateTime.now());
    }

    @Override
    public void postUpdate(User user) {

    }

    @Override
    public void preDelete(Long t) {

    }

    @Override
    public void postDelete(Long t) {

    }
}
