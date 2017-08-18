package com.jycar.server.test.service;


import com.github.pagehelper.PageInfo;
import com.jycar.server.test.entities.Person;

import java.util.List;

public interface PersonService {

    public List<Person> getAll();

    public Person getById(int id);

    public int update(Person person);

    public int deleteById(int id);

    public int add(Person person);

    public PageInfo<Person> getPage(int current, int rows);
}
