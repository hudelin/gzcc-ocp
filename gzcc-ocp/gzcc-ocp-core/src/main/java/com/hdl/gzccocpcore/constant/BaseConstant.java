package com.hdl.gzccocpcore.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseConstant {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(BaseConstant.class);

    //真
    public static final String TRUE = "1";
    //假
    public static final String FALSE = "0";

    static {
        logger.debug("加载本地数据....");
        logger.info("加载本地数据....");
    }
}
