package com.jycar.server.test.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jycar.server.common.utils.JyComUtils;
import com.jycar.server.test.entities.Person;
import com.jycar.server.test.mapper.PersonMapper;
import com.jycar.server.test.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("personService")
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonMapper personMapper;

    @Override
    public List<Person> getAll() {
        return personMapper.getAll();
    }

    @Override
    public Person getById(int id) {
        return personMapper.getById(id);
    }

    @Transactional
    @Override
    public int update(Person person) {
        int num = personMapper.update(person);
        JyComUtils.randomDisaster();
        return num;
    }

    @Transactional
    @Override
    public int deleteById(int id) {
        int num = personMapper.deleteById(id);
        JyComUtils.randomDisaster();
        return num;
    }

    @Transactional
    @Override
    public int add(Person person) {
        int num = personMapper.add(person);
        JyComUtils.randomDisaster();
        return num;
    }

    @Override
    public PageInfo<Person> getPage(int currentPageNum, int eachPageRows) {
        PageHelper.startPage(currentPageNum, eachPageRows);
        List<Person> list = this.getAll();
        PageInfo<Person> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

}
