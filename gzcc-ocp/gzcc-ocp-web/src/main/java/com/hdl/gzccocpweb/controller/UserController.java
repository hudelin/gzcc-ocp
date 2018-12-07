package com.hdl.gzccocpweb.controller;


import com.hdl.gzccocpcore.entity.User;
import com.hdl.gzccocpcore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    private RequestCache requestCache=new HttpSessionRequestCache();

    @Autowired
    private RedisTemplate redisCacheTemplate;

    @RequestMapping(value = "/login")
    public ModelAndView register(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        SavedRequest savedRequest = requestCache.getRequest(httpServletRequest, httpServletResponse);
        String targetUrl="/";
        try {
            targetUrl = savedRequest.getRedirectUrl();
        }catch (Exception e){

        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",targetUrl);
        mv.setViewName("/user/login.btl");
        return mv;
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<User> findAll() throws Exception {
//        String imageCode = (String) redisCacheTemplate.opsForValue().get("imageCode");
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
