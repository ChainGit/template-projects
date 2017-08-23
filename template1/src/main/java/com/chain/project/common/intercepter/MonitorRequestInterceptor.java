package com.chain.project.common.intercepter;

import com.chain.project.common.directory.Constant;
import com.chain.project.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 检测请求，屏蔽/test/
 */
public class MonitorRequestInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(MonitorRequestInterceptor.class);
    @Autowired
    private AppConfig appConfig;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String requestPath = httpServletRequest.getServletPath();

        if (!Constant.PROD_MODE.equals(appConfig.getProperty("spring.profiles.active"))) {
            String contextPath = httpServletRequest.getContextPath();
            String requestQueryString = httpServletRequest.getQueryString();
            String fullUrlPath = contextPath + (requestPath == null ? "" : requestPath) +
                    ", queryString: " + (requestQueryString == null ? "empty" : requestQueryString);
            logger.info("Request URL: " + fullUrlPath);
        } else {
            //生产环境不处理test请求
            if (requestPath != null && requestPath.startsWith("/test"))
                return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
