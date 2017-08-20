package com.chain.project.test.service.impl;

import com.chain.project.base.service.impl.AbstractService;
import com.chain.project.common.utils.JyComUtils;
import com.chain.project.test.entities.Person;
import com.chain.project.test.mapper.PersonMapper;
import com.chain.project.test.service.PersonService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("personService")
public class PersonServiceImpl extends AbstractService<Person, Integer> implements PersonService {

    @Autowired
    private PersonMapper personMapper;

    @Override
    public List<Person> queryListAll() {
        return personMapper.queryListAll();
    }

    @Override
    public Person findById(Integer id) {
        return personMapper.findById(id);
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
    public int insert(Person person) {
        int num = personMapper.insert(person);
        JyComUtils.randomDisaster();
        return num;
    }

    @Override
    public PageInfo<Person> getPage(int currentPageNum, int eachPageRows) {
        PageHelper.startPage(currentPageNum, eachPageRows);
        List<Person> list = this.queryListAll();
        PageInfo<Person> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

}
