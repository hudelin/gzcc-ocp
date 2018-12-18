package com.hdl.gzccocpweb.controller;

import com.hdl.gzccocpcore.entity.Note;
import com.hdl.gzccocpcore.entity.Reply;
import com.hdl.gzccocpcore.service.NoteService;
import com.hdl.gzccocpcore.service.ReplyService;
import com.hdl.gzccocpcore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @RequestMapping(value = "/save")
    @ResponseBody
    public Note save(Note note,Long userId) throws Exception {
        note.setUser(userService.get(userId));
        note = noteService.save(note);

        return note;
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public Note get(Long noteId) throws Exception {
        Note note = noteService.get(noteId);
        return note;
    }

    @RequestMapping(value = "/findAll")
    @ResponseBody
    public List<Note> findAll() throws Exception {
        List<Note> noteList = noteService.findAll();
        return noteList;
    }

    @RequestMapping(value = "/saveReplay")
    @ResponseBody
    public Reply replay(Reply reply, Long noteId, Long userId) throws Exception {
        reply.setNote(noteService.get(noteId));
        reply.setUser(userService.get(userId));
        reply = replyService.save(reply);
        return reply;
    }

    @RequestMapping(value = "/acceptReplay")
    @ResponseBody
    public Reply acceptReplay(Long replyId, Long noteId) throws Exception {
//        Reply reply=replyService.get(replyId);
//        reply.setIsAccept(true);
//        replyService.save(reply);
        return replyService.acceptReply(replyId, noteId);
//        return reply;
    }

    @RequestMapping(value = "/deleteReplay")
    @ResponseBody
    public void deleteReplay(Long replyId) throws Exception {
        replyService.delete(replyId);
    }


    @RequestMapping(value = "/findReplyPage")
    @ResponseBody
    public Page<Reply> findReplyPage(Long noteId, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        Page<Reply> replyPage = replyService.findPageByNoteId(page, size, noteId);
        return replyPage;
    }

    @RequestMapping(value = "/findNotePage")
    @ResponseBody
    public Page<Note> findNotePage(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        Note note=new Note();
        Page<Note> notePage = noteService.findPageByCondition(note,page, size);
        return notePage;
    }


}
