package com.jycar.server.test.service;


import com.github.pagehelper.PageInfo;
import com.jycar.server.base.service.BaseService;
import com.jycar.server.test.entities.Person;

import java.util.List;

public interface PersonService extends BaseService<Person, Integer> {

    List<Person> queryListAll();

    Person findById(Integer id);

    int update(Person person);

    int deleteById(int id);

    int insert(Person person);

    PageInfo<Person> getPage(int current, int rows);
}
