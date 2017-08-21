package com.chain.project.test.mapper;

import com.chain.project.base.mapper.BaseMapper;
import com.chain.project.test.entities.Person;
import org.springframework.stereotype.Component;

import java.util.List;

//java可以也仅仅可以多继承（或者实现）接口，类是单继承
@Component
public interface PersonMapper extends BaseMapper<Person, Long> {

    int insert(Person person);

    int update(Person user);

    int deleteById(Long id);

    Person findById(Long id);

    List<Person> queryListAll();
}
