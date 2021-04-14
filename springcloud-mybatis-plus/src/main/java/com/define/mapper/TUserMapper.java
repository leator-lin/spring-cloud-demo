package com.define.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.define.entity.TUser;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Lea
 * @since 2020-12-08
 */
public interface TUserMapper extends BaseMapper<TUser> {
    IPage<TUser> pageList(Page<TUser> page);
}
