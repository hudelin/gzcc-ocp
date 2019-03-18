package com.hdl.gzccocpweb.controller;


import com.hdl.gzccocpcore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        httpServletRequest.getSession().setAttribute("user",userService.findByAccount(authentication.getName()));
        ModelAndView mv = new ModelAndView();
        mv.addObject("user",userService.findByAccount(authentication.getName()));
        mv.setViewName("/admin/index.btl");
        return mv;
    }

    @RequestMapping("/user")
    public ModelAndView user(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/admin/user.btl");
        return mv;
    }

    @RequestMapping("/teacher")
    public ModelAndView teacher(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/admin/teacher.btl");
        return mv;
    }

    @RequestMapping("/major")
    public ModelAndView major(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/admin/major.btl");
        return mv;
    }

    @RequestMapping("/note")
    public ModelAndView note(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/admin/note.btl");
        return mv;
    }

    @RequestMapping("/reply")
    public ModelAndView reply(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/admin/reply.btl");
        return mv;
    }

//    @RequestMapping("/teachingTeam")
//    public ModelAndView teachingTeam(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("/admin/teachingTeam.btl");
//        return mv;
//    }



}
