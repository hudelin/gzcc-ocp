package com.hdl.gzccocpweb.controller;

import com.hdl.gzccocpcore.dto.NoteDTO;
import com.hdl.gzccocpcore.entity.Note;
import com.hdl.gzccocpcore.entity.Reply;
import com.hdl.gzccocpcore.entity.Resource;
import com.hdl.gzccocpcore.response.ObjectRestResponse;
import com.hdl.gzccocpcore.service.NoteService;
import com.hdl.gzccocpcore.service.ReplyService;
import com.hdl.gzccocpcore.service.ResourceService;
import com.hdl.gzccocpcore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/edit/{noteId}")
    public ModelAndView edit(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,@PathVariable Long noteId) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("noteId",noteId);
        mv.setViewName("/note/edit.btl");
        return mv;
    }

    @RequestMapping(value = "/detail/{noteId}")
    public ModelAndView detail(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable Long noteId) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.addObject("noteId", noteId);
        mv.setViewName("/note/detail.btl");
        return mv;
    }

    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/note/index.btl");
        return mv;
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public Note save(Note note,Long userId) throws Exception {
        if(note.getId()==null){
            note.setUser(userService.get(userId));
            note = noteService.save(note);
        }else{
            note.setUser(userService.get(userId));
            note = noteService.update(note);
        }

        return note;
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public ObjectRestResponse get(Long noteId) throws Exception {
        NoteDTO noteDTO = noteService.getDTO(noteId,NoteDTO.class);
        if(!StringUtils.isEmpty(noteDTO.getResource())){
            String[] resourceSrcList=noteDTO.getResource().split(",");
            List<Resource> resourceList=new ArrayList<>();
            for(String s:resourceSrcList){
                resourceList.add(resourceService.findByFormatName(s)) ;
            }
            noteDTO.setResourceList(resourceList);
        }
        return new ObjectRestResponse(noteDTO);
    }

    @RequestMapping(value = "/findAll")
    @ResponseBody
    public List<Note> findAll() throws Exception {
        List<Note> noteList = noteService.findAll();
        return noteList;
    }

    @RequestMapping(value = "/findReplyPage")
    @ResponseBody
    public Page<Reply> findReplyPage(Long noteId, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        Page<Reply> replyPage = replyService.findPageByNoteId(page, size, noteId);
        return replyPage;
    }

    @RequestMapping(value = "/findNotePage")
    @ResponseBody
    public Page<Note> findNotePage(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size,String orderType) throws Exception {
        Note note=new Note();
        Page<Note> notePage = noteService.findPageByConditionAndSort(note,page, size,new Sort(Sort.Direction.DESC, "top").and(new Sort(Sort.Direction.DESC,"lastModifiedTime")));
        return notePage;
    }




}
