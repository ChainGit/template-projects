package com.jycar.server.test.entities;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * 实体类一般遵循下面的规范：
 * 　　　　1、根据你的设计，定义一组你需要的私有属性。
 * 　　　　2、根据这些属性，创建它们的setter和getter方法。（eclipse等集成开发软件可以自动生成。具体怎么生成？请自行百度。）
 * 　　　　3、提供带参数的构造器和无参数的构造器。
 * 　　　　4、重写父类中的eauals()方法和hashcode()方法。（如果需要涉及到两个对象之间的比较，这两个功能很重要。）
 * 　　　　5、实现序列化并赋予其一个版本号。
 */
@Repository
@Data
public class Person implements Serializable {

    private long id;
    private String name;
    private int age;
}
