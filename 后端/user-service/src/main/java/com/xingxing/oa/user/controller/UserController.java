package com.xingxing.oa.user.controller;


import com.xingxing.oa.user.entity.User;
import com.xingxing.oa.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int add(@RequestBody User user){
        return userService.addOne(user);
    }

    @GetMapping(value = "/{id}")
    public User getById(@PathVariable("id") Long userId){
        return userService.getById(userId);
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<User> getByIds(@RequestBody(required = false) List<Long> ids,
                               @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
                               @RequestParam(value = "pageSize", required = false) Integer pageSize){
        return userService.getByIds(ids);
    }
}
