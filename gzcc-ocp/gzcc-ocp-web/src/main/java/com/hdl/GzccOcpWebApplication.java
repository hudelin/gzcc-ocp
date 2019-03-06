package com.hdl;

import com.hdl.gzccocpweb.webSocket.WebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@EnableSwagger2
@EnableJpaAuditing
public class GzccOcpWebApplication {

    public static void main(String[] args) {
//        SpringApplication.run(GzccOcpWebApplication.class, args);

        SpringApplication springApplication = new SpringApplication(GzccOcpWebApplication.class);
        ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);
        //解决WebSocket不能注入的问题
        WebSocketServer.setApplicationContext(configurableApplicationContext);

    }
}
