package com.hdl.gzccocpcore.validate.code;

import com.hdl.gzccocpcore.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ImageCodeGenerator implements ValidateCodeGenerator {

    /**
     * 系统配置
     */
    @Autowired
    private SecurityProperties securityProperties;

//    @Autowired
//    private RedisTemplate redisCacheTemplate;

    @Override
    public ImageCode generate(ServletWebRequest servletWebRequest) {
        int width = ServletRequestUtils.getIntParameter(servletWebRequest.getRequest(), "width",
                securityProperties.getValidateCodeProperties().getImageCodeProperties().getWidth());
        int height = ServletRequestUtils.getIntParameter(servletWebRequest.getRequest(), "height",
                securityProperties.getValidateCodeProperties().getImageCodeProperties().getHeight());
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 30));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        String sRand = "";
        for (int i = 0; i < securityProperties.getValidateCodeProperties().getImageCodeProperties().getLength(); i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 20 * i + 5, 25);
        }

        g.dispose();
        servletWebRequest.getRequest().getSession().setAttribute("code",sRand);
        ImageCode imageCode = new ImageCode(image, sRand, securityProperties.getValidateCodeProperties().getImageCodeProperties().getExpireIn());
//        redisCacheTemplate.opsForValue().set("imageCode", imageCode.getCode());
        return imageCode;
    }
    /**
     * 生成随机背景条纹
     *
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
