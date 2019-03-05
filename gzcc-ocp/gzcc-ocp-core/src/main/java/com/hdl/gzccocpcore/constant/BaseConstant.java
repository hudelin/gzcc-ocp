package com.hdl.gzccocpcore.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseConstant {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(BaseConstant.class);


    /**
     * 系统错误
     */
    public static final String SYSTEM_ERROR = "0000";



    static {
        logger.debug("debug加载本地数据....");
        logger.info("info加载本地数据....");
    }
}
