package com.define.commons.proxy;

import java.util.Map;

/**
* 公共待办代理类
* @Author: huanght
* @Date: 20181031 
*/
public abstract class CommonProxy<T> {

	/**
	 * 设置排他网关下一环节变量
	 * @param taskName   当前任务名称
	 * @param variables  流程变量集合
	 * @param conditionMap 存条件变量对应的值，如：conditionMap.put("days", 4)
	 * @param isBack 是否回退操作
	 */
	public void findConditionValue(String taskName, Map<String, Object> variables, Map<String, Object> conditionMap, boolean isBack) {
		
	}
}
