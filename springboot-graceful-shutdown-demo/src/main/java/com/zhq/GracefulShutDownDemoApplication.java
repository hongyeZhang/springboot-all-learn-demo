package com.zhq;

import com.zhq.tomcat.GracefulShutdownTomcat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GracefulShutDownDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GracefulShutDownDemoApplication.class, args);
    }

    @Autowired
    private GracefulShutdownTomcat gracefulShutdownTomcat;

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addConnectorCustomizers(gracefulShutdownTomcat);
        return tomcat;
    }


}
