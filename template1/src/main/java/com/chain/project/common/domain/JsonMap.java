package com.chain.project.common.domain;

import com.chain.project.common.exception.ChainProjectRuntimeException;
import com.chain.project.common.utils.JyComUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

/**
 * Map的继承类，方便操作Json的Map对象
 * <p>
 * <p>
 * Jackson会在Converter里自动的对常见的基本数据类进行转换值。
 * 但注意{"id":1}和{"id":"1"}不是一个意思，后者只会转成String
 *
 * @author Chain
 */
public class JsonMap extends HashMap<String, Object> {

    private static Logger logger = LoggerFactory.getLogger(JsonMap.class);

    public Object get(String key) {
        return super.get(key);
    }

    // TODO 如果需要接收的数据类型比较复杂，比如有BigInteger,BigDecimal等等
    // TODO 此时建议使用Map中原生的get(String key)返回未转换的Object进行单独处理
    // TODO 也可以request.getAttribute(Constant.JSON_MAP)获得原始的字符串
    public <T> T get(String key, Class<T> clz) {
        Object obj = get(key);
        if (obj != null) {
            Class<?> objClass = obj.getClass();
            if (objClass.equals(clz)) {
                return (T) obj;
            } else if (obj instanceof Number) {
                //jackson默认将int范围内的接收到的值转为int,long范围的是long,float同理
                //目前只能考虑常见的转换，建议实体类统一使用包装类
                if (obj instanceof Integer && clz.equals(Long.class)) {
                    Integer i = (Integer) obj;
                    Long lo = Long.valueOf(i.longValue());
                    return (T) lo;
                } else if (obj instanceof Float && clz.equals(Double.class)) {
                    Float f = (Float) obj;
                    Double lo = Double.valueOf(f.doubleValue());
                    return (T) lo;
                }
            }
            // FIXME 这里可能会出现转换异常
            try {
                return (T) obj;
            } catch (Exception e) {
                logger.error("convert exception", e);
                // throw new ChainProjectRuntimeException("map value of '" + key + "' convert to '" + clz.getClass() + "' fail");
                return null;
            }
        }
        return null;
    }

    public String getString(String key) {
        return get(key, String.class);
    }

    public int getInteger(String key) {
        return get(key, Integer.class);
    }

    public float getFloat(String key) {
        return get(key, Float.class);
    }

    public long getLong(String key) {
        return get(key, Long.class);
    }

    public double getDouble(String key) {
        return get(key, Double.class);
    }

    public boolean getBoolean(String key) {
        return get(key, Boolean.class);
    }

    public boolean isString(Object obj) {
        return obj != null && obj instanceof String;
    }

    public int getParsedInteger(String key) {
        String s = getCheckedString(key);
        int value = Integer.parseInt(s);
        return value;
    }


    public long getParsedLong(String key) {
        String s = getCheckedString(key);
        long value = Long.parseLong(s);
        return value;
    }

    public float getParsedFloat(String key) {
        String s = getCheckedString(key);
        float value = Float.parseFloat(s);
        return value;
    }

    public double getParsedDouble(String key) {
        String s = getCheckedString(key);
        double value = Double.parseDouble(s);
        return value;
    }

    public boolean getParsedBoolean(String key) {
        String s = getCheckedString(key);
        boolean value = Boolean.parseBoolean(s);
        return value;
    }

    private String getCheckedString(String key) {
        Object obj = get(key);
        if (!isString(obj))
            throw new ChainProjectRuntimeException("map value of '" + key + "' is not a string");
        String s = (String) obj;
        return s;
    }

    //本框架中约定时间以long型数与前端交互
    public Date getDate(String key) {
        long longDate = getLong(key);
        return new Date(longDate);
    }

    //使用Java8的新时间API，也可以使用Joda-Time
    public String getFormatDateString(String key) {
        Date date = getDate(key);
        return JyComUtils.toFormatDateString(date);
    }

    //Java8的新时间API之间是使用新API的类，通过Instant和旧的Date进行转换，DateTimeFormatter和旧的Format进行转换
    //新时间API的类均为无状态的，和String一样，是不可变对象，因此所有的操作均是返回新的对象
    public String getFormatDateString(String key, DateTimeFormatter dateTimeFormatter) {
        Date date = getDate(key);
        return JyComUtils.toFormatDateString(date, dateTimeFormatter);
    }

}
