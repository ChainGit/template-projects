package com.jycar.server.test.mapper;

import com.jycar.server.base.mapper.BaseMapper;
import com.jycar.server.test.entities.Person;
import org.springframework.stereotype.Component;

import java.util.List;

//java可以也仅仅可以多继承（或者实现）接口，类是单继承
@Component
public interface PersonMapper extends BaseMapper<Person, Integer> {

    int insert(Person person);

    int update(Person user);

    int deleteById(Integer id);

    Person findById(Integer id);

    List<Person> queryListAll();
}
