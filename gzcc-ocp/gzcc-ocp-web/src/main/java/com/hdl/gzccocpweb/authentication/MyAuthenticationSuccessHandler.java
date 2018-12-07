package com.hdl.gzccocpweb.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hdl.gzccocpcore.properties.SecurityProperties;
import com.hdl.gzccocpcore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * json 转换工具类
     */
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SecurityProperties securityProperties;
    private RequestCache requestCache=new HttpSessionRequestCache();

    @Autowired
    private UserService userService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        logger.info("登录成功");

        //判断是json 格式返回 还是 view 格式返回
//        if (LoginResponseType.JSON.equals(securityProperties.getWebProperties().getLoginResponseType())){
//            //将 authention 信息打包成json格式返回
            httpServletResponse.setContentType("application/json;charset=UTF-8");
        try {
            httpServletRequest.getSession().setAttribute("user",userService.findByUsername(authentication.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(authentication));
//        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(targetUrl));
//        }else {
//            //返回view
//            super.onAuthenticationSuccess(httpServletRequest,httpServletResponse,authentication);
//        }

    }
}


