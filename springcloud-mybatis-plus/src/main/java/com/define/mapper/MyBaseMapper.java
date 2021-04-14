package com.define.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.define.entity.TUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyBaseMapper<T> extends BaseMapper<TUser> {

    int mysqlInsertAllBatch(@Param("list") List<T> batchList);

}
