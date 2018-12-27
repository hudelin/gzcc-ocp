package com.hdl.gzccocpweb.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/admin/index.btl");
        return mv;
    }

    @RequestMapping("/user")
    public ModelAndView user(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/admin/user.btl");
        return mv;
    }

}
