package com.hdl.gzccocpweb.controller;

import com.hdl.gzccocpcore.constant.OcpErrorConstant;
import com.hdl.gzccocpcore.dto.ChatDTO;
import com.hdl.gzccocpcore.dto.GroupChatDTO;
import com.hdl.gzccocpcore.dto.MessageDTO;
import com.hdl.gzccocpcore.dto.UserDTO;
import com.hdl.gzccocpcore.entity.Friend;
import com.hdl.gzccocpcore.entity.GroupChat;
import com.hdl.gzccocpcore.entity.Message;
import com.hdl.gzccocpcore.entity.User;
import com.hdl.gzccocpcore.exception.BaseException;
import com.hdl.gzccocpcore.response.ObjectRestResponse;
import com.hdl.gzccocpcore.service.FriendService;
import com.hdl.gzccocpcore.service.GroupChatService;
import com.hdl.gzccocpcore.service.MessageService;
import com.hdl.gzccocpcore.service.UserService;
import com.hdl.gzccocpweb.webSocket.WebSocketServer;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private UserService userService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private GroupChatService groupChatService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private WebSocketServer webSocketServer;

    @RequestMapping("/test")
    public ModelAndView index(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/chat/test.btl");
        return mv;
    }

    @RequestMapping("/message")
    public ModelAndView message(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/chat/message.btl");
        return mv;
    }

    @RequestMapping("/find")
    public ModelAndView find(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/chat/find.btl");
        return mv;
    }

    @RequestMapping("/json")
    @ResponseBody
    public ObjectRestResponse getJson(Long userId) throws Exception {
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setMine(userService.getDTO(userId, UserDTO.class));
        chatDTO.setFriend(friendService.findByUserId(userId));
        List<GroupChatDTO> groupChatDTOList=groupChatService.transToDTOList(groupChatService.findByUserId(userId), GroupChatDTO.class);
        for(GroupChatDTO groupChatDTO:groupChatDTOList){
            String name=groupChatDTO.getGroupname()+"("+groupChatDTO.getId()+")";
            groupChatDTO.setGroupname(name);
        }
        chatDTO.setGroup(groupChatDTOList);
        return new ObjectRestResponse(chatDTO);
    }

    @RequestMapping("/getMembers")
    @ResponseBody
    public ObjectRestResponse getMembers(Long id) throws Exception {
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setList(userService.findByGroupChatId(id));
        return new ObjectRestResponse(chatDTO);
    }

    @RequestMapping("/socket/{cid}")
    public ModelAndView socket(@PathVariable String cid) {
        ModelAndView mav = new ModelAndView("/socket");
        mav.addObject("cid", cid);
        return mav;
    }

    //推送数据接口
    @ResponseBody
    @RequestMapping("/socket/push/{cid}")
    public void pushToWeb(@PathVariable Long cid, String message) {
        try {
            WebSocketServer.sendInfo(message, cid);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return ApiReturnUtil.success(cid);
    }

    @ResponseBody
    @RequestMapping("/createGroup")
    public ObjectRestResponse createGroup(Long userId,Friend friend) throws Exception {
        List<User> userList=new ArrayList<>();
        userList.add(userService.get((long) 2));
        friend.setGroupname("我的好友");
        friend.setList(userList);
        friend.setUser(userService.get(userId));
        friendService.save(friend);
        return new ObjectRestResponse(friend);
    }

    @ResponseBody
    @RequestMapping("/send")
    public ObjectRestResponse send(String message,Long userId) throws Exception {
        throw new BaseException(OcpErrorConstant.ACCOUNT_NOTE_COLLECT,"帖子已收藏");
//        return new ObjectRestResponse();
    }


    @ResponseBody
    @RequestMapping("/readerMessage")
    public ObjectRestResponse readerMessage(Long userId) throws Exception {
        List<Message> messageList = messageService.getReaderMessage(userId);
        for(Message message:messageList){
            message.setUser(userService.get(message.getFormUserId()));
        }
        return new ObjectRestResponse(messageList);
    }




}
