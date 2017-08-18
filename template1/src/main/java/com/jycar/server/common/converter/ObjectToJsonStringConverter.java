package com.jycar.server.common.converter;

import com.chain.utils.ReflectionUtils;
import com.chain.utils.crypto.CryptoFactoryBean;
import com.chain.utils.crypto.RSAUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jycar.server.common.domain.Result;
import com.jycar.server.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

@Component
public class ObjectToJsonStringConverter extends MappingJackson2HttpMessageConverter {

    private static Logger logger = LoggerFactory.getLogger(ObjectToJsonStringConverter.class);

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private CryptoFactoryBean cryptoFactoryBean;

    // 重写 writeInternal 方法，在返回内容前首先进行加密，注意是有Type的父类方法
    @Override
    public void writeInternal(Object object, Type type,
                              HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {
//        logger.info("MyMappingJackson2HttpMessageConverter writeInternal");
//        super.writeInternal(object, type, outputMessage);
        ObjectMapper mapper = new ObjectMapper();
        if (object == null) {
            //创建一个空的Result对象
            Result result = new Result();
            if (appConfig.isEncrypt())
                result.setEncrypt(true);
            else
                result.setEncrypt(false);
            object = result;
        }
        boolean resultEncrypt = true;
        if (object instanceof Result) {
            Result result = (Result) object;
            resultEncrypt = result.isEncrypt();
            result.setEncrypt(null);
            //这个方法没用
            //ignoreResultValue(result);
            //设置的mapper忽略只会忽略Result中的属性，但是不会忽略Result内存储的data的null值
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        String json = mapper.writeValueAsString(object);
        if (appConfig.isEncrypt() && resultEncrypt) {
            RSAUtils rsaUtils = cryptoFactoryBean.getRsaUtils(true);
            json = rsaUtils.encryptByPrivateKey(json);
        }
        OutputStream body = outputMessage.getBody();
        body.write(json.getBytes());
        body.flush();
    }

    /**
     * 根据ignore忽略值，只适合POJO，暂未支持集合以及集合的嵌套
     *
     * @param result
     */
    private void ignoreResultValue(Result result) {
        String[] ignores = result.getIgnore();
        if (ignores == null || ignores.length < 1)
            return;
        Object data = result.getData();
        if (data != null) {
            try {
                for (String s : ignores) {
                    Field f = ReflectionUtils.getDeclaredField(data, s);
                    if (f != null)
                        ReflectionUtils.setField(data, f, null);
                }
            } catch (Exception e) {
                logger.error("ignoreResultValue" + e);
            }
        }
    }

}