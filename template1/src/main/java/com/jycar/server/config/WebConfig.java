package com.jycar.server.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

//相当于原来的web.xml，Servlet3.0
//如果是war包运行方式，则需要另外创建类，实现implements WebApplicationInitializer，且加上@Order(1)
//如果是jar包运行方式，则使用这个类
//默认自动配置，不需要手动配置，不过也可以使用@Bean的方式来配置，来继承(覆盖)自动配置
@Configuration
public class WebConfig {

    //@Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        //示例
        //servletRegistrationBean.addUrlMappings("/demo-servlet2");
        //servletRegistrationBean.setServlet(new DemoServlet2());
        return servletRegistrationBean;
    }

    //@Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        Set<String> set = new HashSet<>();
        //示例
        //filterRegistrationBean.setFilter(new OpenSessionInViewFilter());
        //set.add("/");
        filterRegistrationBean.setUrlPatterns(set);
        return filterRegistrationBean;
    }

    //@Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean() {
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        //示例
        //servletListenerRegistrationBean.setListener(new Log4jConfigListener());
        //servletListenerRegistrationBean.addInitParameter("log4jConfigLocation", "classpath:log4j.properties");
        return servletListenerRegistrationBean;
    }
}
