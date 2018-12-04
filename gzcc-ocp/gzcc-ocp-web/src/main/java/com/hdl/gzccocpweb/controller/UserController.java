package com.hdl.gzccocpweb.controller;


import com.hdl.gzccocpcore.entity.User;
import com.hdl.gzccocpcore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findAll")
    @ResponseBody
    public List<User> findAll() throws Exception {
        List<User> userList=userService.findAll();
        return userList;
    }

//    @ResponseBody
//    @RequestMapping("/save")
//    public User save(User user) throws Exception {
//        user=userService.save(user);
//        return user;
//    }

}
