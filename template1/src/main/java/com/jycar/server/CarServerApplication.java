package com.jycar.server;

import com.jycar.server.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//SpringBootApplication已经包含了ComponentScan和EnableAutoConfiguration
@SpringBootApplication
public class CarServerApplication {

    private Logger logger = LoggerFactory.getLogger(CarServerApplication.class);

    @Autowired
    private AppConfig config;

    public static void main(String[] args) {
        SpringApplication.run(CarServerApplication.class, args);
    }

    @RequestMapping("/")
    public String index() {
        logger.info("application start success!");
        return config.getProperty("app.name") + " " + config.getProperty("app.version")
                + " [" + config.getProperty("spring.profiles.active") + "]";
    }

}


