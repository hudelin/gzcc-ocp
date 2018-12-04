package com.hdl.gzccocpcore.properties;


import org.springframework.beans.factory.annotation.Value;

public class WebProperties {

    @Value("${gzcc.ocp.security.web.loginPage}")
    private String loginPage="/html/user/login.html";

    private LoginResponseType loginResponseType=LoginResponseType.JSON;

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
}
