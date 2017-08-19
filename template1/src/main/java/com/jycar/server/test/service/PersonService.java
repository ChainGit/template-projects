package com.jycar.server.test.service;


import com.github.pagehelper.PageInfo;
import com.jycar.server.test.entities.Person;

import java.util.List;

public interface PersonService {

    List<Person> getAll();

    Person getById(int id);

    int update(Person person);

    int deleteById(int id);

    int add(Person person);

    PageInfo<Person> getPage(int current, int rows);
}
