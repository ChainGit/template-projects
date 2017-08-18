package com.jycar.server.common.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jycar.server.common.domain.JsonMap;
import com.jycar.server.common.exception.CarServerRuntimeException;
import com.jycar.server.common.utils.JyComUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * String转JsonMap
 */
@Component
public class StringToJsonMapConverter implements Converter<String, JsonMap> {

    private Logger logger = LoggerFactory.getLogger(StringToJsonMapConverter.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public JsonMap convert(String s) {
        JsonMap jsonMap = null;
        //在JsonString拦截器处理后，s不会为空
        if (JyComUtils.isEmpty(s)) {
//            jsonMap = new JsonMap();
            throw new CarServerRuntimeException("empty json string value");
        }
        try {
            //会自动的判断s中的值类型并自动转换，比如{"id"=123,"status"=true}，会自动转成Integer和Boolean
            jsonMap = objectMapper.readValue(s, JsonMap.class);
        } catch (IOException e) {
            logger.error("io exception", e);
            throw new CarServerRuntimeException("not a json string", e);
        }
        return jsonMap;
    }
}
