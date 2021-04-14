package com.define.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.define.entity.TUser;
import org.apache.ibatis.annotations.Param;

public interface BigDataHandleMapper extends BaseMapper<TUser> {

    int getGroupIdByIdRange(@Param("id") int id);

    int getTableNumByGroupId(@Param("groupId") int groupId);

    int getDbIdByGroupIdAndHashValue(@Param("groupId") int groupId, @Param("hashIndex") int hashIndex);

    int getTableIdByIdRange(@Param("dbId") int dbId, @Param("id") int id);

    String getTablenameByTableId(@Param("tableId") int tableId);

    void saveDataByTablename(@Param("tablename") String tablename, @Param("user") TUser user);
}
