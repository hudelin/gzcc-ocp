package com.hdl.gzccocpweb.controller;

import com.hdl.gzccocpcore.entity.Note;
import com.hdl.gzccocpcore.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @RequestMapping(value = "/add")
    public ModelAndView add(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/note/add.btl");
        return mv;
    }

    @RequestMapping(value = "/edit")
    public ModelAndView edit(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/note/edit.btl");
        return mv;
    }

    @RequestMapping(value = "/detail/{id}")
    public ModelAndView detail(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable Long id) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.addObject("note",noteService.get(id));
//        mv.addObject()
        mv.setViewName("/note/detail.btl");
        return mv;
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public Note save(Note note) throws Exception {
        note=noteService.save(note);
        return note;
    }
    @RequestMapping(value = "/get")
    @ResponseBody
    public Note get(Long id) throws Exception {
        Note note = noteService.get(id);
        return note;
    }

    @RequestMapping(value = "/findAll")
    @ResponseBody
    public List<Note> findAll() throws Exception {
        List<Note> noteList = noteService.findAll();
        return noteList;
    }







}
