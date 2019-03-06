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

    static {
        logger.debug("debug加载本地数据....");
        logger.info("info加载本地数据....");

        resourceTypeMap.put(RESOURCE_TYPE_NOTE,"RESOURCE_TYPE_NOTE");

    }
}
