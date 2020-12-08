package com.define.service;

import com.define.dao.UserDao;
import com.define.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * xxx
 *
 * @Author: linyincheng
 * @Date: 2019/6/3 17:25
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<UserDTO> getUsersByIdList(List<String> idList) {
        return userDao.getUsersByIdList(idList);
    }
}
