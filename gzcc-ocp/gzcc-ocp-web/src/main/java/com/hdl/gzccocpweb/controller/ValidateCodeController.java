package com.hdl.gzccocpweb.controller;

import com.hdl.gzccocpcore.properties.SecurityProperties;
import com.hdl.gzccocpcore.validate.code.ImageCode;
import com.hdl.gzccocpcore.validate.code.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ValidateCodeController {

    private static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

//    private SessionStrategy sessionStrategy=new HttpSessionStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    @RequestMapping("/code/image")
    public void createCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {


        ImageCode imageCode=imageCodeGenerator.generate(new ServletWebRequest(httpServletRequest));
//        String imageCode1= (String) redisCacheTemplate.opsForValue().get("imageCode");
//        redisCacheTemplate.opsForValue().set("imageCode",imageCode.getCode());
        httpServletRequest.getSession().setAttribute("imageCode",imageCode.getCode());
        ImageIO.write(imageCode.getBufferedImage(),"JPEG",httpServletResponse.getOutputStream());
//        ServletRequestUtils.getIntParameter(httpServletRequest,"width",securityProperties.getValidateCodeProperties().getImageCodeProperties().getWidth());
    }
}
