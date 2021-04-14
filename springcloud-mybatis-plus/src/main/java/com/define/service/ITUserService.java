package com.define.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.define.entity.TUser;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Lea
 * @since 2020-12-08
 */
public interface ITUserService extends IService<TUser> {
    IPage<TUser> pageList(Page<TUser> page);
}
