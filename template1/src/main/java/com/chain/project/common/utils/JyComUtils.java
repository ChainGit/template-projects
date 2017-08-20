package com.chain.project.common.utils;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * JYCOM工具类
 *
 * @author lsh
 * @author chain
 */
public class JyComUtils {

    private static Logger logger = LoggerFactory.getLogger(JyComUtils.class);

    public static Boolean isEmpty(String s) {
        return s == null || s.length() <= 0;
    }

    public static Boolean isEmpty(List<?> list) {
        return null == list || list.size() == 0;
    }

    public static Boolean isEmpty(Map<?, ?> map) {
        return null == map || map.size() == 0;
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
                    Object o = map.get(property.getName());
                    if (o != null)
                        setter.invoke(obj, map.get(property.getName()));
                }
            }

            return (T) obj;
        } catch (Exception e) {
            logger.warn("map to object", e);
            throw new ChainProjectRuntimeException("map to object", e);
        }
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
    public static String[] getDefaultIgnoreArr() {
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
