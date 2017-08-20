package com.chain.project.base.mapper;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

//其他Mapper继承这个接口，已实现druid监控
public interface BaseMapper<T, ID extends Serializable> {

    int insert(T t);

    int insertBatch(List<T> t);

    int deleteBatchById(List<ID> ids);

    int deleteById(@Param("id") ID id);

    int deleteById(@Param("id") String id);

    int update(T t);

    T find(Map<String, Object> parameter);

    T findById(@Param("id") ID id);

    T findById(@Param("id") String id);

    T findByName(@Param("name") String name);

    List<T> queryListAll();

    List<T> queryListAll(Map<String, Object> parameter);

    List<T> queryListByPage(Map<String, Object> parameter);

    List<T> queryListByPageNext(Map<String, Object> parameter);

    int count(Map<String, Object> parameter);
}
