package com.hdl.gzccocpcore.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;


@ConfigurationProperties(prefix = "gzcc.ocp.security.web")
@PropertySource("classpath:application.properties")
public class SecurityProperties {

//    private String loginPage;
//
//    public String getLoginPage() {
//        return loginPage;
//    }
//
//    public void setLoginPage(String loginPage) {
//        this.loginPage = loginPage;
//    }

    private WebProperties webProperties = new WebProperties();

    private ValidateCodeProperties validateCodeProperties=new ValidateCodeProperties();

    public WebProperties getWebProperties() {
        return webProperties;
    }

    public void setWebProperties(WebProperties webProperties) {
        this.webProperties = webProperties;
    }

    public ValidateCodeProperties getValidateCodeProperties() {
        return validateCodeProperties;
    }

    public void setValidateCodeProperties(ValidateCodeProperties validateCodeProperties) {
        this.validateCodeProperties = validateCodeProperties;
    }
}
