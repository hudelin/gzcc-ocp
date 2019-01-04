package com.hdl.gzccocpweb.controller;

import com.hdl.gzccocpcore.entity.Note;
import com.hdl.gzccocpcore.entity.Reply;
import com.hdl.gzccocpcore.entity.Resource;
import com.hdl.gzccocpcore.service.NoteService;
import com.hdl.gzccocpcore.service.ReplyService;
import com.hdl.gzccocpcore.service.UserService;
import com.hdl.gzccocpweb.support.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public Page<Note> findNotePage(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size,String orderType) throws Exception {
        Note note=new Note();
        Page<Note> notePage = noteService.findPageByConditionAndSort(note,page, size,new Sort(Sort.Direction.DESC, "isTop").and(new Sort(Sort.Direction.DESC,"lastModifiedTime")));
        return notePage;
    }

    @RequestMapping("/upload")
    @ResponseBody
    private ObjectRestResponse upload(MultipartFile file){
//        try {  /*获取文件的后缀，对文件进行重命名*/
//            String prefix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
//            String fileName = user.getName() + "." + prefix;
//            File f = new File(rootPath + fileName);
//            file.transferTo(f);
//            user.setPicture("/uploads/" + fileName);
//            userRepository.save(user);
//        } catch (FileNotFoundException e) {
//        } catch (IOException e) {
//        }
        Resource resource=new Resource();
        resource.setSrc("https://res.layui.com/static/images/layui/logo.png");
        ObjectRestResponse uploadResponse=new ObjectRestResponse(resource);
        return uploadResponse;
    }


}
