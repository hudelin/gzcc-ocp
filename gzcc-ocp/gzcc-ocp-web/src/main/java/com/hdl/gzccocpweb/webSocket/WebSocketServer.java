package com.hdl.gzccocpweb.webSocket;


import com.hdl.gzccocpcore.dto.ChatDTO;
import com.hdl.gzccocpcore.dto.MessageDTO;
import com.hdl.gzccocpcore.dto.SendDTO;
import com.hdl.gzccocpcore.dto.UserDTO;
import com.hdl.gzccocpcore.entity.Role;
import com.hdl.gzccocpcore.response.ObjectRestResponse;
import com.hdl.gzccocpcore.service.MessageService;
import net.sf.json.JSONObject;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/webSocket/{sid}")
@Component
public class WebSocketServer {
    static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //此处是解决无法注入的关键
    private static ApplicationContext applicationContext;
    //你要注入的service或者dao
    private MessageService messageService;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketServer.applicationContext = applicationContext;
    }


    //接收sid
    private Long sid = Long.valueOf(0);

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") Long sid) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        log.info("有新窗口开始监听:" + sid + ",当前在线人数为" + getOnlineCount());
        this.sid = sid;
//        try {
//            sendMessage("");
//        } catch (IOException e) {
//            log.error("webSocket IO异常");
//        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口" + sid + "的信息:" + message);
        //群发消息
        JSONObject jsonObject = JSONObject.fromObject(message);
        //如果属性中含有复杂的类型，当其中属性有类似List ,如List<UserDTO> userList, 可以先定义Map<String, Class> classMap = new HashMap<>();在classMap中put你要转换的类中的集合名,像:classMap.put("userList", UserDTO.class)
        Map<String, Class> classMap = new HashMap<>();
        classMap.put("userList", UserDTO.class);
        classMap.put("roleList", Role.class);
        SendDTO sendDTO = (SendDTO) JSONObject.toBean(jsonObject, SendDTO.class,classMap);
        //获取服务实例
        messageService = applicationContext.getBean(MessageService.class);
        List<MessageDTO> messageDTOList=new ArrayList<>();
        try {
            messageDTOList = messageService.createMessage(sendDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(MessageDTO messageDTO:messageDTOList){

            ObjectRestResponse objectRestResponse = new ObjectRestResponse(messageDTO);
            objectRestResponse.setMsg("chatMessage");
            JSONObject json = JSONObject.fromObject(objectRestResponse);//将java对象转换为json对象
            String str = json.toString();
            for (WebSocketServer item : webSocketSet) {
                try {
                    if (!messageDTO.getMine()&&item.sid.equals(messageDTO.getToid())) {
                        item.sendMessage(str);
//                        messageService.readMessage(messageDTO.getCid());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
//        session.getBasicRemote().sendObject();
    }


    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message, @PathParam("sid") Long sid) throws IOException {
        log.info("推送消息到窗口" + sid + "，推送内容:" + message);
        for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if (sid == null) {
                    item.sendMessage(message);
                } else if (item.sid == sid) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}


