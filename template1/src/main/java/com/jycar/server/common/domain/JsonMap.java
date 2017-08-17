package com.jycar.server.common.domain;

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
        return (T) super.get(key);
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

}
