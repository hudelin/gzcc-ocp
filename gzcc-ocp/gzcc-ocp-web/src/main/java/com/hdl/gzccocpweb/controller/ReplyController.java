package com.hdl.gzccocpweb.controller;


import com.hdl.gzccocpcore.dto.ReplyDTO;
import com.hdl.gzccocpcore.entity.Reply;
import com.hdl.gzccocpcore.response.ObjectRestResponse;
import com.hdl.gzccocpcore.service.NoteService;
import com.hdl.gzccocpcore.service.ReplyService;
import com.hdl.gzccocpcore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ObjectRestResponse replay(Reply reply, Long noteId, Long userId) throws Exception {
        if(reply.getId()==null){
            reply.setNote(noteService.get(noteId));
            reply.setUser(userService.get(userId));
            reply = replyService.save(reply);
        }else{
            reply = replyService.update(reply);
        }
        return new ObjectRestResponse (reply);
    }
    @RequestMapping(value = "/changeAccept")
    @ResponseBody
    public ObjectRestResponse acceptReplay(Long id) throws Exception {
        Reply reply = replyService.changeAccept(id);
        return new ObjectRestResponse(reply);
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public void deleteReplay(Long replyId) throws Exception {
        replyService.deleteLogic(replyId);
    }

    @RequestMapping(value = "/praise")
    @ResponseBody
    public void praise(Long replyId,Long userId) throws Exception {
        replyService.praiseReply(replyId,userId);
    }

    @RequestMapping(value = "/findPage")
    @ResponseBody
    public ObjectRestResponse findPage(ReplyDTO replyDTO , @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        Page<Reply> replyPage = replyService.findReplyPage(replyDTO, page, size);
        return new ObjectRestResponse(replyPage);
    }
}
