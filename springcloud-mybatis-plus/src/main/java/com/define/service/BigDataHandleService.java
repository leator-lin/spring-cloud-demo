package com.define.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.define.entity.TUser;

public interface BigDataHandleService extends IService<TUser> {

    void saveData(TUser tUser);
}
