package com.chain.project.test.service;

import com.chain.project.base.service.BaseService;
import com.chain.project.test.entities.Person;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PersonService extends BaseService<Person, Long> {

    List<Person> queryListAll();

    Person findById(Long id);

    int update(Person person);

    int deleteById(Long id);

    int insert(Person person);

    PageInfo<Person> getPage(int current, int rows);
}
