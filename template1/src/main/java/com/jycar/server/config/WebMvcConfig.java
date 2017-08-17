package com.jycar.server.config;

import com.chain.utils.crypto.CryptoFactoryBean;
import com.jycar.server.common.converter.ObjectToJsonStringConverter;
import com.jycar.server.common.converter.StringToJsonMapConverter;
import com.jycar.server.common.intercepter.JsonStringInterceptor;
import com.jycar.server.common.intercepter.MeasurementInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Configuration
@EnableWebMvc
@AutoConfigureAfter({AppConfig.class})
public class WebMvcConfig implements WebMvcConfigurer {

    private Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    @Bean
    public AppConfig appConfig() {
        return new AppConfig();
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer pathMatchConfigurer) {

    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer contentNegotiationConfigurer) {

    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer asyncSupportConfigurer) {

    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer defaultServletHandlerConfigurer) {
//        defaultServletHandlerConfigurer.enable();
    }

    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {
        formatterRegistry.addConverter(stringToJsonMapConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("add interceptors");
        registry.addInterceptor(jsonStringInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new MeasurementInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {

    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

    }

    @Override
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {

    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry viewResolverRegistry) {

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> list) {
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> list) {

    }

    //拦截器的加载再contextLoader之前，所以需要先获取bean
    @Bean
    public JsonStringInterceptor jsonStringInterceptor() {
        return new JsonStringInterceptor();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        logger.info("extendMessageConverters");
        for (int i = 0; i < converters.size(); i++) {
            HttpMessageConverter<?> httpMessageConverter = converters.get(i);
            if (httpMessageConverter.getClass().equals(MappingJackson2HttpMessageConverter.class)) {
                converters.set(i, objectToJsonStringConverter());
            }
        }
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

    }

    @Override
    public Validator getValidator() {
        return null;
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }


    @Bean
    public ObjectToJsonStringConverter objectToJsonStringConverter() {
        return new ObjectToJsonStringConverter();
    }

    @Bean(name = "conversionService")
    public ConversionService conversionService() {
        logger.info("create bean conversionService");
        FormattingConversionServiceFactoryBean conversionServiceFactoryBean = new FormattingConversionServiceFactoryBean();
        Set<Converter> converters = new LinkedHashSet<>();
        converters.add(stringToJsonMapConverter());
        conversionServiceFactoryBean.setConverters(converters);
        return conversionServiceFactoryBean.getObject();
    }

    @Bean
    public StringToJsonMapConverter stringToJsonMapConverter() {
        return new StringToJsonMapConverter();
    }

    @Bean
    public CryptoFactoryBean cryptoFactoryBean() {
        logger.info("create cryptoFactoryBean");
        AppConfig appConfig = appConfig();
        CryptoFactoryBean cryptoFactoryBean = new CryptoFactoryBean();
        String rsaPublicKeyFile = appConfig.getProperty("app.crypto.rsa-public");
        String rsaPrivateKeyFile = appConfig.getProperty("app.crypto.rsa-private");
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        String CLASS_PATH_PREFIX = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX;
        String CLASS_PATH_STRING = "classpath:";
        rsaPublicKeyFile = getFilePath(rsaPublicKeyFile, pathMatchingResourcePatternResolver, CLASS_PATH_STRING);
        rsaPrivateKeyFile = getFilePath(rsaPrivateKeyFile, pathMatchingResourcePatternResolver, CLASS_PATH_STRING);
        cryptoFactoryBean.setRsaPublicKeyFilePath(rsaPublicKeyFile);
        cryptoFactoryBean.setRsaPrivateKeyFilePath(rsaPrivateKeyFile);
        return cryptoFactoryBean;
    }

    private String getFilePath(String s, PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver, String CLASS_PATH_STRING) {
        if (s.startsWith(CLASS_PATH_STRING)) {
            s = CLASS_PATH_STRING + File.separator + s.replaceAll(CLASS_PATH_STRING, "");
            Resource resource = pathMatchingResourcePatternResolver.getResource(s);
            try {
                s = resource.getFile().getAbsolutePath();
            } catch (IOException e) {
                logger.error("io exception", e);
            }
        }
        return s;
    }
}
