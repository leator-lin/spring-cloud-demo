package com.define.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.define.entity.TUser;
import com.define.mapper.TUserMapper;
import com.define.service.ITUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Lea
 * @since 2020-12-08
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {
    @Resource
    private TUserMapper tUserMapper;

    @Override
    public IPage<TUser> pageList(Page<TUser> page) {
        return tUserMapper.pageList(page);
    }
}
