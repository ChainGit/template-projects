package com.chain.project.base.service.impl;

import com.chain.project.base.mapper.BaseMapper;
import com.chain.project.base.service.BaseService;
import com.chain.project.common.exception.ChainProjectRuntimeException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class AbstractService<T, ID extends Serializable> implements BaseService<T, ID> {

    private BaseMapper<T, ID> baseMapper;

    public void setBaseMapper(BaseMapper<T, ID> baseMapper) {
        this.baseMapper = baseMapper;
    }

    //增删改需要捕获异常并抛出

    @Override
    public int insert(T t) {
        try {
            return baseMapper.insert(t);
        } catch (Exception e) {
            throw new ChainProjectRuntimeException(e);
        }
    }

    @Override
    public int insertBatch(List<T> t) {
        try {
            return baseMapper.insertBatch(t);
        } catch (Exception e) {
            throw new ChainProjectRuntimeException(e);
        }
    }

    @Override
    public int deleteById(ID id) {
        try {
            return baseMapper.deleteById(id);
        } catch (Exception e) {
            throw new ChainProjectRuntimeException(e);
        }
    }

    @Override
    public int deleteById(String id) {
        try {
            return baseMapper.deleteById(id);
        } catch (Exception e) {
            throw new ChainProjectRuntimeException(e);
        }
    }

    @Override
    public int update(T t) {
        try {
            return baseMapper.update(t);
        } catch (Exception e) {
            throw new ChainProjectRuntimeException(e);
        }
    }

    @Override
    public int deleteBatchById(List<ID> ids) {
        try {
            return baseMapper.deleteBatchById(ids);
        } catch (Exception e) {
            throw new ChainProjectRuntimeException(e);
        }
    }

    //查操作一般不用检测异常

    @Override
    public T find(Map<String, Object> parameter) {
        return baseMapper.find(parameter);
    }

    @Override
    public T findById(ID id) {
        return baseMapper.findById(id);
    }

    @Override
    public T findById(String id) {
        return baseMapper.findById(id);
    }

    @Override
    public T findByName(String name) {
        return baseMapper.findByName(name);
    }

    @Override
    public List<T> queryListAll() {
        return baseMapper.queryListAll();
    }

    @Override
    public List<T> queryListAll(Map<String, Object> parameter) {
        return baseMapper.queryListAll(parameter);
    }

    @Override
    public List<T> queryListByPage(Map<String, Object> parameter) {
        return baseMapper.queryListByPage(parameter);
    }

    @Override
    public int count(Map<String, Object> parameter) {
        return baseMapper.count(parameter);
    }

}