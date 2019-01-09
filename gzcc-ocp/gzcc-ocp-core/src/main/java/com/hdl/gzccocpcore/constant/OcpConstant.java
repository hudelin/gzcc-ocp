package com.hdl.gzccocpcore.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class OcpConstant {

    private static Logger logger = LoggerFactory.getLogger(BaseConstant.class);

    public static final String RESOURCE_TYPE_NOTE = "1";
//    public static final String RESOURCE_TYPE_NOTE = "1";

    public static Map<String, String> resourceTypeMap = new HashMap<>();

    static {
        logger.debug("debug加载本地数据....");
        logger.info("info加载本地数据....");

        resourceTypeMap.put(RESOURCE_TYPE_NOTE,"RESOURCE_TYPE_NOTE");

    }
}
