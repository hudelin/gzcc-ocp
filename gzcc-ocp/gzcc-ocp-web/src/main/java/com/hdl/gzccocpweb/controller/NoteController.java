package com.hdl.gzccocpweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/note")
public class NoteController {
    @RequestMapping(value = "/add")
    public ModelAndView register(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/note/add.btl");
        return mv;
    }

}
