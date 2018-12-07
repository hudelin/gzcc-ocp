package com.hdl.gzccocpcore.properties;


import org.springframework.beans.factory.annotation.Value;

public class WebProperties {

    @Value("${gzcc.ocp.security.web.loginPage}")
    private String loginPage="/user/login";

    private LoginResponseType loginResponseType=LoginResponseType.JSON;

    private int rememberMeSeconds=3600;

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginResponseType getLoginResponseType() {
        return loginResponseType;
    }

    public void setLoginResponseType(LoginResponseType loginResponseType) {
        this.loginResponseType = loginResponseType;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }
}
