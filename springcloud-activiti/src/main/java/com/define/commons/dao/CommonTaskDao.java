package com.define.commons.dao;

import com.define.commons.activiti.vo.MyApplyVO;
import com.define.commons.dao.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommonTaskDao extends BaseDao<Object, Long> {

	String checkExpre(Map<String, Object> param);

	/***
	 * 查找我的待办
	 * @param userOrRole
	 * @return
	 */
	List<Map> queryToDoTasks(Map<String, Object> userOrRole);

	/***
	 * 我的申请
	 * @param param
	 * @return
	 */
	List<MyApplyVO> listMyApply(Map<String, Object> param);

	/**
	 * 我的申请总数
	 * @param param
	 * @return
	 */
	int countMyApply(Map<String, Object> param);

	/***
	 * 审核历史记录
	 * @param procInsId
	 * @return
	 */
	List<Map> auditHisList(String procInsId);
}
