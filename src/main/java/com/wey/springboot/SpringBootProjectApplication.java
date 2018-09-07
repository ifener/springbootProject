package com.wey.springboot;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import com.wey.spring_boot_starter_hello.HelloService;
import com.wey.springboot.setting.AuthorSetting;

@RestController
@SpringBootApplication
// @EnableJpaRepositories(repositoryBaseClass = CustomRepositoryFactoryBean.class)
public class SpringBootProjectApplication {
    
    @Autowired
    private AuthorSetting authorSetting;
    
    @Autowired
    private HelloService helloService;
    
    public static void main(String[] args) {
        // SpringApplication.run(SpringBootProjectApplication.class, args);
        SpringApplication app = new SpringApplication(SpringBootProjectApplication.class);
        app.run(args);
        // HttpEncodingAutoConfiguration
        // ServerProperties
        
    }
    
    public Connector httpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11AprProtocol");
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        return connector;
    }
}
