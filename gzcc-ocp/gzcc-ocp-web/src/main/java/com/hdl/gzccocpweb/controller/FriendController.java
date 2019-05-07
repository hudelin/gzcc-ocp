package com.hdl.gzccocpweb.controller;

import com.hdl.gzccocpcore.constant.OcpConstant;
import com.hdl.gzccocpcore.dto.MessageDTO;
import com.hdl.gzccocpcore.entity.Friend;
import com.hdl.gzccocpcore.entity.Message;
import com.hdl.gzccocpcore.entity.User;
import com.hdl.gzccocpcore.response.ObjectRestResponse;
import com.hdl.gzccocpcore.service.FriendService;
import com.hdl.gzccocpcore.service.GroupChatService;
import com.hdl.gzccocpcore.service.MessageService;
import com.hdl.gzccocpcore.service.UserService;
import com.hdl.gzccocpweb.webSocket.WebSocketServer;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private UserService userService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private GroupChatService groupChatService;

    @Autowired
    private MessageService messageService;

    @ResponseBody
    @RequestMapping("/changeGroupName")
    public ObjectRestResponse changeGroupName(Long id,String changeGroupName) throws Exception {
        friendService.changeGroupName(id,changeGroupName);
        return new ObjectRestResponse();
    }

    @ResponseBody
    @RequestMapping("/createGroup")
    public ObjectRestResponse createGroup(Long userId,String groupname) throws Exception {
        Friend friend=friendService.createGroup(userId,groupname);
        return new ObjectRestResponse(friend);
    }

    @ResponseBody
    @RequestMapping("/addFriend")
    public ObjectRestResponse addFriend(MessageDTO messageDTO) throws Exception {
        messageService.add(messageDTO);
        JSONObject json = JSONObject.fromObject(messageDTO);//将java对象转换为json对象
        String str = json.toString();
        return new ObjectRestResponse();
    }

    @ResponseBody
    @RequestMapping("/deleteFriend")
    public ObjectRestResponse deleteFriend(Long userId,Long myId) throws Exception {
        friendService.deleteFriend(userId,myId);
        return new ObjectRestResponse();
    }

    @ResponseBody
    @RequestMapping("/agreeFriend")
    public ObjectRestResponse agreeFriend(Long group,Long userId, Long id) throws Exception {
        friendService.addFriend(userId, group);
        Message message=messageService.agreeFriend(id);
        MessageDTO messageDTO=new MessageDTO();
        messageDTO.setType(OcpConstant.CHAT_MESSAGE_TYPE_FRIEDN_MESSAGE);
        messageDTO.setGroupId(message.getGroupId());
        User user=userService.get(message.getToUserId());
        messageDTO.setAvatar(user.getAvatar());
        messageDTO.setUsername(user.getUsername());
        messageDTO.setId(user.getId());
        messageDTO.setSign(user.getSign());
        ObjectRestResponse objectRestResponse = new ObjectRestResponse(messageDTO);
        objectRestResponse.setMsg("addList");
        JSONObject json = JSONObject.fromObject(objectRestResponse);//将java对象转换为json对象
        String str = json.toString();
        try {
            WebSocketServer.sendInfo(str, message.getFormUserId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ObjectRestResponse();
    }
    @ResponseBody
    @RequestMapping("/moveFriend")
    public ObjectRestResponse moveFriend(Long userId, Long id,Long myId) throws Exception {
        friendService.moveFriend(userId, id,myId);
        return new ObjectRestResponse();
    }

    @ResponseBody
    @RequestMapping("/refuseFriend")
    public ObjectRestResponse refuseFriend(Long id) throws Exception {
        messageService.refuseFriend(id);
        return new ObjectRestResponse();
    }

    @ResponseBody
    @RequestMapping("/deleteGroup")
    public ObjectRestResponse deleteGroup(Long id) throws Exception {
        friendService.deleteLogic(id);
        return new ObjectRestResponse();
    }



}
