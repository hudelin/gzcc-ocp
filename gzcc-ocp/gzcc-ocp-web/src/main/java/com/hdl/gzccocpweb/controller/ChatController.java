package com.hdl.gzccocpweb.controller;

import com.hdl.gzccocpcore.dto.ChatDTO;
import com.hdl.gzccocpcore.dto.UserDTO;
import com.hdl.gzccocpcore.entity.Friend;
import com.hdl.gzccocpcore.entity.User;
import com.hdl.gzccocpcore.response.ObjectRestResponse;
import com.hdl.gzccocpcore.service.FriendService;
import com.hdl.gzccocpcore.service.GroupChatService;
import com.hdl.gzccocpcore.service.UserService;
import com.hdl.gzccocpweb.webSocket.WebSocketServer;
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
    private WebSocketServer webSocketServer;

    @RequestMapping("/test")
    public ModelAndView index(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/chat/test.btl");
        return mv;
    }

    @RequestMapping("/json")
    @ResponseBody
    public ObjectRestResponse getJson(Long userId) throws Exception {
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setMine(userService.getDTO(userId, UserDTO.class));
        chatDTO.setFriend(friendService.findByUserId(userId));
        chatDTO.setGroup(groupChatService.findByUserId(userId));
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
//            BeanUtils.copyProperties(user, note);
        } catch (IOException e) {
            e.printStackTrace();
//            return ApiReturnUtil.error(cid+"#"+e.getMsg());
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
        WebSocketServer.sendInfo("123",userId);
        return new ObjectRestResponse();

    }

}
