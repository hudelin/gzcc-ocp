package com.hdl.gzccocpweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class CommonController {
    @RequestMapping()
    public ModelAndView register(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/note/major.btl");
        return mv;
    }
//    @RequestMapping()
//    public ModelAndView register(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("/index.btl");
//        return mv;
//    }
}
