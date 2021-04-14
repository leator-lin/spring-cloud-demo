package com.define.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.define.mapper.MyBaseMapper;
import com.define.methods.MysqlInsertAllBatch;

import java.util.List;

/**
 * 实现自己定义的mapper方法
 * 自定义SqlInjector，实现ISqlInjector接口或者继承AbstractSqlInjector
 */
public class MyLogicSqlInjector extends DefaultSqlInjector {

    /**
     * 如果只需增加方法，保留MP自带方法
     * 可以super.getMethodList() 再add
     *
     * @return
     */
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(MyBaseMapper.class);
        methodList.add(new MysqlInsertAllBatch());
        return methodList;
    }
}