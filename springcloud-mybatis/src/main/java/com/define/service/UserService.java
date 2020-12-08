package com.define.service;

import com.define.dto.UserDTO;

import java.util.List;

/**
 * xxx
 *
 * @Author: linyincheng
 * @Date: 2019/6/3 17:25
 */
public interface UserService {
    List<UserDTO> getUsersByIdList(List<String> idList);
}
