package com.jycar.server.test.mapper;

import com.jycar.server.test.entities.Person;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PersonMapper {

    int add(Person person);

    int update(Person user);

    int deleteById(int id);

    Person getById(int id);

    List<Person> getAll();
}
