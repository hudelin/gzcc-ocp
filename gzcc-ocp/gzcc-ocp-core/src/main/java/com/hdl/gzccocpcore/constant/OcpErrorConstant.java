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
     * 密码错误
     */
    public static final String PASSWORD_ERROR = "10003";
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

    /**
     * 专业模块错误
     */
    public static final String MAJOR_SYNOPSIS_NULL = "50000";
    public static final String MAJOR_PICTURE_NULL = "50001";
    public static final String MAJOR_MEANS_NULL = "50002";
    public static final String MAJOR_INTRODUCTION_NULL = "50003";
    public static final String MAJOR_TEACHER_NULL = "50004";

    public static final String MAJOR_TEACHER_EXIST="50005";


}
