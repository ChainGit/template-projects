package com.chain.project.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//更多配置信息
@Component
@Configuration
@PropertySource("classpath:more.properties")
@AutoConfigureBefore(AppConfig.class)
public class MoreConfig {

}
