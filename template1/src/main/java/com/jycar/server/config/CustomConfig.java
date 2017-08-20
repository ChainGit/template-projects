package com.jycar.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

//一些自定义的配置可以放在这，比如Kaptcha验证码生成器等
@Configuration
public class CustomConfig {

    private Logger logger = LoggerFactory.getLogger(CustomConfig.class);

}
