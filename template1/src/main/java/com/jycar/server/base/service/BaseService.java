package com.jycar.server.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseService<T, ID extends Serializable> {

    int insert(T t);

    int insertBatch(List<T> t);

    int deleteBatchById(List<ID> ids);

    int deleteById(ID id);

    int deleteById(String id);

    int update(T t);

    T find(Map<String, Object> parameter);

    T findById(ID id);

    T findById(String id);

    T findByName(String name);

    List<T> queryListAll();

    List<T> queryListAll(Map<String, Object> parameter);

    List<T> queryListByPage(Map<String, Object> parameter);

    int count(Map<String, Object> parameter);
}