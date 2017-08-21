package com.chain.project.common.utils;

import com.chain.project.common.directory.Constant;
import com.chain.project.common.exception.ChainProjectRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * JYCOM工具类
 *
 * @author lsh
 * @author chain
 */
public class JyComUtils {

    private static Logger logger = LoggerFactory.getLogger(JyComUtils.class);

    //线程安全的
    private static DateTimeFormatter dateTimeFormatter;

    public static Boolean isEmpty(List<?> list) {
        return null == list || list.size() == 0;
    }

    public static Boolean isEmpty(Map<?, ?> map) {
        return null == map || map.size() == 0;
    }

    static {
        dateTimeFormatter = DateTimeFormatter.ofPattern(Constant.DATETIME_PATTERN);
    }

    public static Boolean isEmpty(String s) {
        return s == null || s.length() < 1;
    }

    public static Boolean isEmpty(Object obj) {
        return null == obj;
    }

    public static Boolean isEmpty(Object[] obj) {
        return null == obj || obj.length < 1;
    }

    public static Boolean isZero(Number number) {
        if (number instanceof Integer || number instanceof Short
                || number instanceof Byte || number instanceof Long || number instanceof BigInteger) {
            Long t = number.longValue();
            if (t == 0)
                return true;
        } else if (number instanceof Float || number instanceof Double
                || number instanceof BigDecimal) {
            BigDecimal t = new BigDecimal(number.doubleValue());
            if (t.equals(new BigDecimal(0)))
                return true;
        }
        return false;
    }

    /**
     * 数组合并(增长)
     *
     * @param oldArry
     * @param newArry
     * @return
     */
    public static String[] concatStringArray(String[] oldArry, String[] newArry) {
        int lenOld = oldArry.length;
        int lenNew = newArry.length;
        String[] temp = new String[lenNew + lenOld];
        int i = 0;
        for (; i < lenOld; i++)
            temp[i] = oldArry[i];
        for (; i < lenNew + lenOld; i++)
            temp[i + lenOld] = newArry[i - lenOld];
        return temp;
    }

    public static Boolean isPositive(Number number) {
        if (number instanceof Integer || number instanceof Short
                || number instanceof Byte || number instanceof Long || number instanceof BigInteger) {
            Long t = number.longValue();
            if (t > 0)
                return true;
        } else if (number instanceof Float || number instanceof Double
                || number instanceof BigDecimal) {
            BigDecimal t = new BigDecimal(number.doubleValue());
            if (t.compareTo(new BigDecimal(0)) > 0)
                return true;
        }
        return false;
    }

    /**
     * 对象转map
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> objectToMap(Object obj) {
        if (obj == null)
            return null;

        try {
            Map<String, Object> map = new HashMap<String, Object>();

            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo
                    .getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (key.compareToIgnoreCase("class") == 0) {
                    continue;
                }
                Method getter = property.getReadMethod();
                Object value = getter != null ? getter.invoke(obj) : null;
                map.put(key, value);
            }
            return map;
        } catch (Exception e) {
            logger.warn("object to map", e);
            throw new ChainProjectRuntimeException("object to map", e);
        }
    }

    public static Boolean isNegative(Number number) {
        if (number instanceof Integer || number instanceof Short
                || number instanceof Byte || number instanceof Long || number instanceof BigInteger) {
            Long t = number.longValue();
            if (t < 0)
                return true;
        } else if (number instanceof Float || number instanceof Double
                || number instanceof BigDecimal) {
            BigDecimal t = new BigDecimal(number.doubleValue());
            if (t.compareTo(new BigDecimal(0)) < 0)
                return true;
        }
        return false;
    }

    /**
     * map转对象
     *
     * @param map
     * @param beanClass
     * @return
     * @throws Exception
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) {
        if (isEmpty(map))
            return null;

        try {
            Object obj = beanClass.newInstance();

            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo
                    .getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                Method setter = property.getWriteMethod();
                if (setter != null) {
                    Object valObj = map.get(property.getName());
                    if (valObj != null) {
                        Class[] params = setter.getParameterTypes();
                        if (JyComUtils.isEmpty(params))
                            continue;
                        Class<?> valObjClass = valObj.getClass();
                        Class<?> param0Class = params[0];
                        // 目前只能考虑常见的转换，建议实体类统一使用包装类
                        if (!valObjClass.equals(param0Class)) {
                            if (valObjClass.equals(Integer.class) && param0Class.equals(Long.class)) {
                                Integer i = (Integer) valObj;
                                valObj = Long.valueOf(i.longValue());
                            } else if (valObjClass.equals(Float.class) && param0Class.equals(Double.class)) {
                                Float f = (Float) valObj;
                                valObj = Double.valueOf(f.doubleValue());
                            }
                        }
                        // FIXME 这里可能发生异常，比如Integer对象赋给了Long的属性
                        // TODO 不过基本数据类型int赋值为long没有问题
                        setter.invoke(obj, valObj);
                    }
                }
            }

            return (T) obj;
        } catch (Exception e) {
            logger.warn("map to object", e);
            throw new ChainProjectRuntimeException("map to object", e);
        }
    }

    public static long convertDateToLong(Date date) {
        if (date == null)
            throw new ChainProjectRuntimeException("data is null");
        return date.getTime();
    }

    public static Date convertLongToDate(Long lo) {
        if (lo == null)
            throw new ChainProjectRuntimeException("long is null");
        return new Date(lo);
    }

    public static DateTimeFormatter getDefaultDateTimeFormatter() {
        return dateTimeFormatter;
    }

    //使用java8的新时间API，也可以使用Joda-Time
    public static String toFormatDateString(Date date) {
        return toFormatDateString(date, dateTimeFormatter);
    }

    //使用java8的新时间API，也可以使用Joda-Time
    public static Date parseDateFormString(String string) {
        return parseDateFormString(string, dateTimeFormatter);
    }

    //使用java8的新时间API，也可以使用Joda-Time
    public static String toFormatDateString(Date date, DateTimeFormatter formatter) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        return formatter.format(localDateTime);
    }

    //使用java8的新时间API，也可以使用Joda-Time
    public static Date parseDateFormString(String string, DateTimeFormatter formatter) {
        LocalDateTime dateTime = LocalDateTime.parse(string, formatter);
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = dateTime.atZone(zoneId).toInstant();
        return Date.from(instant);
    }

    /**
     * 判断文件类型是否是图片
     *
     * @param file
     * @return
     */
    public static boolean isImage(File file) {
        boolean flag = false;
        try {
            ImageInputStream is = ImageIO.createImageInputStream(file);

            Iterator<ImageReader> iter = ImageIO.getImageReaders(is);
            if (!iter.hasNext()) {
                // 文件不是图片
                return flag;
            }
            is.close();
            flag = true;
        } catch (Exception e) {
            logger.warn("is image", e);
        }
        return flag;
    }

    /**
     * 默认需要过滤的字段数组
     *
     * @return
     */
    public static String[] getDefaultIgnoreArray() {
        String[] arr = new String[]{"delete_flag", "createTime", "updateTime"};
        return arr;
    }

    public static String getRootPath(Class<?> classz) {
        String classPath = classz.getClassLoader().getResource("/").getPath();
        String rootPath = "";
        // windows下
        if ("\\".equals(File.separator)) {
            rootPath = classPath.substring(1,
                    classPath.indexOf("/WEB-INF/classes"));
            rootPath = rootPath.replace("/", "\\");
        }
        // linux下
        if ("/".equals(File.separator)) {
            rootPath = classPath.substring(0,
                    classPath.indexOf("/WEB-INF/classes"));
            rootPath = rootPath.replace("\\", "/");
        }
        return rootPath;
    }

    /**
     * 仅用于测试事务，随机制造Exception，慎用！
     */
    public static void randomDisaster() {
        //0-9的随机数
        int r = (int) (Math.random() * 10 + 0);
        // 3/10的异常发生率
        if (r % 4 == 0) {
            logger.error("random disaster");
            throw new ChainProjectRuntimeException("==== dont't worry, just a random disaster ====");
        }
    }
}
