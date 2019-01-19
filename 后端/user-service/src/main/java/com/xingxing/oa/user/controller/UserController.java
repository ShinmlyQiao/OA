package com.xingxing.oa.user.controller;


import com.xingxing.oa.user.entity.User;
import com.xingxing.oa.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/add",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int addUser(@RequestBody User user){
        return userService.addUser(user);
    }
}
