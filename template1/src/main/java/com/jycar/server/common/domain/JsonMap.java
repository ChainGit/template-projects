package com.jycar.server.common.domain;

import com.jycar.server.common.exception.CarServerRuntimeException;

import java.util.HashMap;

/**
 * Map的继承类，方便操作Json的Map对象
 *
 * @author Chain
 */
public class JsonMap extends HashMap<String, Object> {

    // Jackson会在Converter里自动的对常见的基本数据类进行转换值。
    // 但注意{"id":1}和{"id":"1"}不是一个意思，后者只会转成String

    public <T> T get(String key, Class<T> clz) {
        Object obj = super.get(key);
        if (obj != null && obj.getClass().equals(clz))
            return (T) super.get(key);
        else
            return null;
//            throw new CarServerRuntimeException("map value of '" + key + "' convert to '" + clz.getClass() + "' fail");
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
            throw new CarServerRuntimeException("map value of '" + key + "' is not a string");
        String s = (String) obj;
        return s;
    }

    public Object get(String key) {
        return super.get(key);
    }
}
