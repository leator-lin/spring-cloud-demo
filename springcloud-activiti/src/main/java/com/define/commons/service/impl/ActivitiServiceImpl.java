package com.define.commons.service.impl;

import com.define.commons.proxy.CommonProxy;
import com.define.commons.service.ActivitiService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * xxx
 *
 * @Author: Lea
 * @Date: 2019/1/8 11:52
 */
public class ActivitiServiceImpl implements ActivitiService {
    @Autowired
    private RepositoryService repositoryService;

    /**
     * 选择下一条线
     * 排他网关的输入线，不能有条件设置 ，${condition=='flow'}
     * type 代理类类型,
     * isBack 是否回退操作，true为回退
     * */
    private boolean findNextChoice(ActivityImpl currActivity, ActivityImpl destinationActivity,
                                   Map<String, Object> variables, String type, ProcessDefinitionEntity definitionEntity, boolean isBack) {
        List<PvmTransition> pvmList = destinationActivity.getOutgoingTransitions();
        CommonProxy<?> commonProxy = getObjectBySpring(type);
        if(commonProxy == null)
            return false;
        Map<String,Object> conditionMap = new HashMap<>();
        commonProxy.findConditionValue(currActivity.getProperty("name").toString(),variables,conditionMap,isBack);
        if(pvmList != null && pvmList.size() >0
                ||destinationActivity.getProperty("type").toString().equals("endEvent")){
            String describe = "";
            if(destinationActivity.getProperty("type").toString().equals("exclusiveGateway")){
                for(PvmTransition pvm : pvmList){
                    describe = (String) pvm.getProperty("documentation");//{count}>9
                    describe = StringEscapeUtils.unescapeXml(describe);
                    if(!StringUtils.isEmpty(describe) && describe.indexOf("{") !=-1 && describe.indexOf("}") !=-1){
                        String name = describe.substring(describe.indexOf("{")+1,describe.indexOf("}"));
                        String expre =  describe.replace("{"+name +"}", conditionMap.get(name)+"");
                        Map<String,Object> param = new HashMap<>();
                        param.put("expre", expre);
                        String result = commonTaskDao.checkExpre(param);
                        if(!StringUtils.isEmpty(result)){
                            variables.put(CONDITION, pvm.getId());
                            break;
                        }
                    }
                }
            }
            if(StringUtils.isEmpty(describe) && variables.get(CONDITION) == null
                    && StringUtils.isEmpty((String)variables.get(CONDITION))){
                List<PvmTransition> incomeList = destinationActivity.getIncomingTransitions();
                for(PvmTransition in : incomeList){
                    if(in.getSource().getId().equals(currActivity.getId())){
                        variables.put(CONDITION, in.getId());
                        break;
                    }
                }
            }
            if(variables.get(CONDITION) == null && StringUtils.isEmpty((String)variables.get(CONDITION)))
                return false;
            else if(pvmList.size() == 1){
                //主要是为了处理并行回退
                //destinationActivity 是并行网关，并且并行网关的下一个节点是排他网关
                ActivityImpl targetActivity = (ActivityImpl) pvmList.get(0).getDestination();//排他网关
                if(destinationActivity.getProperty("type").toString().equals("parallelGateway")
                        && targetActivity.getProperty("type").toString().equals("exclusiveGateway")){//目标节点还是排他网关
                    return findNextChoice(currActivity,targetActivity,variables,type,definitionEntity,isBack);
                }
            }
        }
        return true;
    }

    /***
     * 从spring容器获取对象
     * @param type
     * @return
     */
    @Override
    public CommonProxy<?> getObjectBySpring(String type) {
        try {
            CommonProxy<?> proxy = (CommonProxy<?>) Class.forName(type).newInstance();
            if(proxy == null){
                throw new RuntimeException("创建代理类失败！");
            }else{
                proxy = applicationContext.getBean(proxy.getClass());
                return proxy;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void getActivitiObject(String procIns) {
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(procIns.getProcessDefinitionId());
        //发送流程
        ActivityImpl currActivity = definitionEntity.findActivity(task.getTaskDefinitionKey());
        List<PvmTransition> outgoingList = currActivity.getOutgoingTransitions();
        ActivityImpl destinationActivity = null;
        if(outgoingList!=null && outgoingList.size() == 1) {
            destinationActivity = (ActivityImpl) outgoingList.get(0).getDestination();
        }else{
            throw new RuntimeException("第一个任务出现多个分支或找不到下一个环节！");
        }
        if(StringUtils.isEmpty(opinion))
            opinion = "";
        RS rs = doComplete(task.getId(),destinationActivity.getId(),procDefKey,opinion,"发起申请");
        if(rs.getCode().equals(HttpStatus.ERROR.getCode()))
            throw new RuntimeException("发起申请失败！");
    }
}
