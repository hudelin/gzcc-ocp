package com.hdl.gzccocpcore.validate.code;


import com.hdl.gzccocpcore.properties.SecurityProperties;
import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private Set<String> urls = new HashSet<>();

    private SecurityProperties securityProperties;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

//    @Autowired
//    private RedisTemplate redisCacheTemplate;

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String[] configUrls = securityProperties.getValidateCodeProperties().getImageCodeProperties().getUrl().split(",");
        System.out.print(configUrls);
        for (String configUrl : configUrls) {
            urls.add(configUrl);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, javax.servlet.FilterChain filterChain) throws ServletException, IOException {
//        if(httpServletRequest.getRequestURI().equals("/authentication/require")&&httpServletRequest.getMethod().equalsIgnoreCase("POST")){
        boolean action = false;
        for (String url : urls) {
            if (pathMatcher.match(url, httpServletRequest.getRequestURI())) {
                action = true;
            }
        }
        if (action) {
            try {
                validate(new ServletWebRequest(httpServletRequest));
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
//        }
    }

    private void validate(ServletWebRequest servletWebRequest) {

//        String imageCode = (String) redisCacheTemplate.opsForValue().get("imageCode");
        String codeInRequest = servletWebRequest.getParameter("imageCode");
        String imageCode= (String) servletWebRequest.getRequest().getSession().getAttribute("imageCode");
        if (StringUtils.isEmpty(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }
//        if (codeInSession == null) {
//            throw new ValidateCodeException( "验证码不存在");
//        }
//
//        if (codeInSession.isExpried()) {
//            sessionStrategy.removeAttribute(request, sessionKey);
//            throw new ValidateCodeException("验证码已过期");
//        }
//
        if (!imageCode.equals(codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
