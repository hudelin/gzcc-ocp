package com.hdl.gzccocpcore.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OcpErrorConstant {
    private static Logger logger = LoggerFactory.getLogger(BaseConstant.class);

    /**
     * 用户错误
     */
    public static final String USER_ERROR = "10000";
    /**
     * 账号已存在
     */
    public static final String ACCOUNT_EXIST = "10001";
    /**
     * 帖子已收藏
     */
    public static final String ACCOUNT_NOTE_COLLECT = "10002";
    /**
     * 帖子错误
     */
    public static final String NOTE_ERROR = "20000";
    /**
     * 回复错误
     */
    public static final String REPLY_ERROR = "30000";
    /**
     * 资源错误
     */
    public static final String RESOURCE_ERROR = "40000";
    public static final String RESOURCE_IS_NULL = "40001";




}
