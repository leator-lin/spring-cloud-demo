package com.define.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.define.entity.TUser;
import com.define.mapper.BigDataHandleMapper;
import com.define.service.BigDataHandleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BigDataHandleServiceImpl extends ServiceImpl<BigDataHandleMapper, TUser> implements BigDataHandleService {

    @Resource
    private BigDataHandleMapper bigDataHandleMapper;

    @Override
    public void saveData(TUser tUser) {
        // 1，根据范围range定位是哪个group组
        int groupId = bigDataHandleMapper.getGroupIdByIdRange(tUser.getId());

        // 2，根据hash方案定位是哪个DB
        // 2.1，确定DB后，获取DB的table数量
        // 2.2，根据id % table数量确定是在哪个DB
        int tableNum = bigDataHandleMapper.getTableNumByGroupId(groupId);
        int hashIndex = tUser.getId() % tableNum;
        int dbId = bigDataHandleMapper.getDbIdByGroupIdAndHashValue(groupId, hashIndex);

        // 3，根据range方案定位哪个Table
        int tableId = bigDataHandleMapper.getTableIdByIdRange(dbId, tUser.getId());
        String tablename = bigDataHandleMapper.getTablenameByTableId(tableId);
        bigDataHandleMapper.saveDataByTablename(tablename, tUser);
    }
}
