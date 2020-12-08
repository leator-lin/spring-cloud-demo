package com.define.dao;

import com.define.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * xxx
 *
 * @Author: linyincheng
 * @Date: 2019/6/3 17:00
 */
@Mapper
public interface UserDao {
    List<UserDTO> getUsers();

    List<UserDTO> getUsersByIdList(@Param("idList") List<String> idList);
}
