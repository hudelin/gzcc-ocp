package com.hdl.gzccocpweb.controller;


import com.hdl.gzccocpcore.entity.Reply;
import com.hdl.gzccocpcore.service.NoteService;
import com.hdl.gzccocpcore.service.ReplyService;
import com.hdl.gzccocpcore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;
    @Autowired
    private UserService userService;
    @Autowired
    private NoteService noteService;

    @RequestMapping(value = "/save")
    @ResponseBody
    public Reply replay(Reply reply, Long noteId, Long userId) throws Exception {
        reply.setNote(noteService.get(noteId));
        reply.setUser(userService.get(userId));
        if(reply.getId()==null){
            reply = replyService.save(reply);
        }else{
            reply = replyService.update(reply);
        }

        return reply;
    }
    @RequestMapping(value = "/accept")
    @ResponseBody
    public Reply acceptReplay(Long replyId, Long noteId) throws Exception {
        return replyService.acceptReply(replyId, noteId);
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public void deleteReplay(Long replyId) throws Exception {
        replyService.delete(replyId);
    }

    @RequestMapping(value = "/praise")
    @ResponseBody
    public void praise(Long replyId,Long userId) throws Exception {
        replyService.praiseReply(replyId,userId);
    }
}
