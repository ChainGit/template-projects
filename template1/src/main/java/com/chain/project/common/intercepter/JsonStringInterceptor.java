package com.chain.project.common.intercepter;

import com.chain.utils.crypto.CryptoFactoryBean;
import com.chain.utils.crypto.RSAUtils;
import com.chain.project.common.directory.Constant;
import com.chain.project.common.utils.JyComUtils;
import com.chain.project.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JsonStringInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(JsonStringInterceptor.class);

    @Autowired
    private CryptoFactoryBean cryptoFactoryBean;

    @Autowired
    private AppConfig appConfig;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        logger.info("perHandle");
        RSAUtils rsaUtils = cryptoFactoryBean.getRsaUtils(true);
        /* 约定：
         * 1、加密的数据参数key为"s"，不加密的数据参数key为"ns".
         * 2、先处理s，再处理ns，若s不为空，则ns不再处理.
         */
        String jsonEncryptStr = httpServletRequest.getParameter(Constant.REQUEST_ENCRYPT_JSON_KEY);
        String jsonPlainStr = httpServletRequest.getParameter(Constant.REQUEST_PLAIN_JSON_KEY);
        if (!JyComUtils.isEmpty(jsonEncryptStr)) {
            if (appConfig.isEncrypt()) {
                String jsonDecryptStr = null;
                try {
                    jsonDecryptStr = rsaUtils.decryptByPrivateKey(jsonEncryptStr);
                } catch (Exception e) {
                    logger.error("not a correct encrypted json string");
                }
                if (jsonDecryptStr == null)
                    return false;
                httpServletRequest.setAttribute(Constant.JSON_MAP, jsonDecryptStr);
            } else {
                httpServletRequest.setAttribute(Constant.JSON_MAP, jsonEncryptStr);
            }
        } else if (!JyComUtils.isEmpty(jsonPlainStr)) {
            httpServletRequest.setAttribute(Constant.JSON_MAP, jsonPlainStr);
        } else {
            //两者均为空，创建一个空的JSON_MAP
            httpServletRequest.setAttribute(Constant.JSON_MAP, "{}");
        }

        //非生成环境打印request请求中的"s"和"ns"数据，也就是Attribute中的JSON_MAP
        if (!Constant.PROD_MODE.equals(appConfig.getProperty("spring.profiles.active"))) {
            logger.info("Request JsonMap Parameter: " + httpServletRequest.getAttribute(Constant.JSON_MAP));
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        logger.info("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
