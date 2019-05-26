package com.define.commons.service.impl;

import com.define.commons.service.ActivitiService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * xxx
 *
 * @Author: linyincheng
 * @Date: 2019/5/21 18:21
 */
@Service
public class ActivitiServiceImpl implements ActivitiService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    /**
     * 将节点之后的节点删除然后指向新的节点。
     *
     * @param actDefId       流程定义ID
     * @param nodeId         流程节点ID
     * @param aryDestination 需要跳转的节点
     * @return Map<String,Object> 返回节点和需要恢复节点的集合。
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> prepare(String actDefId, String nodeId, String[] aryDestination) {
        Map<String, Object> map = new HashMap<String, Object>();

        //修改流程定义
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(actDefId);
        ActivityImpl curAct = processDefinition.findActivity(nodeId);
        List<PvmTransition> outTrans = curAct.getOutgoingTransitions();
        try {
            //这里先注释，FileUtil.cloneObject报错了
//            List<PvmTransition> cloneOutTrans = (List<PvmTransition>) FileUtil.cloneObject(outTrans);
//            map.put("outTrans", cloneOutTrans);
        } catch (Exception ex) {

        }

        /**
         * 解决通过选择自由跳转指向同步节点导致的流程终止的问题。
         * 在目标节点中删除指向自己的流转。
         */
        for (Iterator<PvmTransition> it = outTrans.iterator(); it.hasNext(); ) {
            PvmTransition transition = it.next();
            PvmActivity activity = transition.getDestination();
            List<PvmTransition> inTrans = activity.getIncomingTransitions();
            for (Iterator<PvmTransition> itIn = inTrans.iterator(); itIn.hasNext(); ) {
                PvmTransition inTransition = itIn.next();
                if (inTransition.getSource().getId().equals(curAct.getId())) {
                    itIn.remove();
                }
            }
        }


        curAct.getOutgoingTransitions().clear();

        if (aryDestination != null && aryDestination.length > 0) {
            for (String dest : aryDestination) {
                //创建一个连接
                ActivityImpl destAct = processDefinition.findActivity(dest);
                TransitionImpl transitionImpl = curAct.createOutgoingTransition();
                transitionImpl.setDestination(destAct);
            }
        }

        map.put("activity", curAct);
        return map;

    }

    /**
     * 将临时节点清除掉，加回原来的节点。
     *
     * @param map void
     */
    @SuppressWarnings("unchecked")
    public void restore(Map<String, Object> map) {
        ActivityImpl curAct = (ActivityImpl) map.get("activity");
        List<PvmTransition> outTrans = (List<PvmTransition>) map.get("outTrans");
        curAct.getOutgoingTransitions().clear();
        curAct.getOutgoingTransitions().addAll(outTrans);
    }

    /**
     * 通过指定目标节点，实现任务的跳转
     *
     * @param taskId      任务ID
     * @param destNodeIds 跳至的目标节点ID
     * @param vars        流程变量
     */
    public synchronized void completeTask(String taskId, String[] destNodeIds, Map<String, Object> vars) {
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();

        String curNodeId = task.getTaskDefinitionKey();
        String actDefId = task.getProcessDefinitionId();

        Map<String, Object> activityMap = prepare(actDefId, curNodeId, destNodeIds);
        try {
            taskService.complete(taskId);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            //恢复
            restore(activityMap);
        }
    }
}
