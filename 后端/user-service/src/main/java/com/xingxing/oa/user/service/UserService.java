package com.xingxing.oa.user.service;

import com.xingxing.oa.user.dao.UserMapper;
import com.xingxing.oa.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public int addUser(User user){
        Objects.requireNonNull(user.getTenantId(),"租户id不能为空");
        log.info("插入用户:{}", user);
        return userMapper.insert(user);
    }

}
