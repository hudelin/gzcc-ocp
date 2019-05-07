package com.hdl.gzccocpcore.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class OcpConstant {

    private static Logger logger = LoggerFactory.getLogger(BaseConstant.class);

    public static final String RESOURCE_TYPE_NOTE = "1";
    public static final String RESOURCE_TYPE_USER_AVATAR = "2";
//    public static final String RESOURCE_TYPE_NOTE = "1";

    public static Map<String, String> resourceTypeMap = new HashMap<>();

    public static final String CHAT_TYPE_CHAT_MESSAGE = "chatMessage";
    public static final String CHAT_TYPE_ADD_LIST= "addList";

    public static final String CHAT_MESSAGE_TYPE_FRIEDN_MESSAGE = "friend";
    public static final String CHAT_MESSAGE_TYPE_GROUP_MESSAGE = "group";
    public static final String CHAT_MESSAGE_TYPE_ADD = "add";
    public static final String CHAT_MESSAGE_TYPE_REPLY = "reply";

    public static final String ROLE_ADMINISTRATOR = "ADMINISTRATOR";
    public static final String ROLE_USER = "USER";
    public static final String ROLE_TEACHER = "TEACHER";

    public static final String NOTE_TYPE_QUESTION = "1";
    public static final String NOTE_TYPE_SHARE = "2";
    public static final String NOTE_TYPE_DISCUSS = "3";
    public static final String NOTE_TYPE_SUGGEST = "4";
    public static final String NOTE_TYPE_NOTICE= "5";
//    public static final String NOTE_TYPE_DYNAMIC = "6";

    public static final String ADD_FRIEND= "0";
    public static final String AGREE= "1";
    public static final String REFUSE= "2";

    public static final String ADD_CONTENT= "申请添加你为好友";
    public static final String REFUSE_CONTENT= "拒绝了你的好友申请";
    public static final String AGREE_CONTENT= "已经同意你的好友申请";

    public static final String REPLY_CONTENT= "回复了您的帖子";




    static {
        logger.debug("debug加载本地数据....");
        logger.info("info加载本地数据....");

        resourceTypeMap.put(RESOURCE_TYPE_NOTE,"RESOURCE_TYPE_NOTE");

    }
}
