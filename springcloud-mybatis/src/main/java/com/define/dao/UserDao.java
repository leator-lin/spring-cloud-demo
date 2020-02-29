package com.define.dao;

import com.define.dto.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * xxx
 *
 * @Author: linyincheng
 * @Date: 2019/6/3 17:00
 */
@Mapper
public interface UserDao {
    List<User> getUsers();
}
