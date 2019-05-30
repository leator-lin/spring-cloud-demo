package com.define.commons.service;

/**
 * xxx
 *
 * @Author: linyincheng
 * @Date: 2019/5/27 16:06
 */

import com.define.commons.utils.UUIDUtil;
import org.activiti.engine.*;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.db.ListQueryParameterObject;
import org.activiti.engine.impl.persistence.entity.*;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*

* 2013.01.25

*

* 任务回退

*

* 需求：从当前任务 任意回退至已审批任务

* 方法：通过activiti源代码里的sqlSession直接修改数据库

*

* 第一步 完成历史TASK覆盖当前TASK

* 用hi_taskinst修改当前ru_task

* ru_task.ID_=hi_taskinst.ID_

* ru_task.NAME_=hi_taskinst.NAME_

* ru_task.TASK_DEF_KEY_=hi_taskinst.TASK_DEF_KEY_

*

* 第二步

* 修改当前任务参与人列表

* ru_identitylink 用ru_task.ID_去ru_identitylink 索引

* ru_identitylink.TASK_ID_=hi_taskinst.ID_

* ru_identitylink.USER_ID=hi_taskinst.ASSIGNEE_

*

* 第三步修改流程记录节点 把ru_execution的ACT_ID_ 改为hi_taskinst.TASK_DEF_KEY_

*

* author:pvii007

* version:1.0

*/
@Service
public class DbTaskReturnService {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;


    public static final int I_NO_OPERATION = 0;

    public static final int I_DONE = 1;

    public static final int I_TASK_NOT_FOUND = 2;

    public static final int I_ROLLBACK = 3;


    /*
    * 实现回退方法
    */
    public int dbBackTo(String currentTaskId, String backToTaskId) {

        int result = DbTaskReturnService.I_NO_OPERATION;
        SqlSession sqlSession = getSqlSession();
        TaskEntity currentTaskEntity = getCurrentTaskEntity(currentTaskId);
        HistoricTaskInstanceEntity backToHistoricTaskInstanceEntity = getHistoryTaskEntity(backToTaskId);

        if (currentTaskEntity == null || backToHistoricTaskInstanceEntity == null) {
            return DbTaskReturnService.I_TASK_NOT_FOUND;
        }

        String processDefinitionId = currentTaskEntity.getProcessDefinitionId();
        String executionId = currentTaskEntity.getExecutionId();
        String currentTaskEntityId = currentTaskEntity.getId();
        String backToHistoricTaskInstanceEntityId = backToHistoricTaskInstanceEntity.getId();
        String backToTaskDefinitionKey = backToHistoricTaskInstanceEntity.getTaskDefinitionKey();
        String backToAssignee = backToHistoricTaskInstanceEntity.getAssignee();


        boolean success = false;
        try {

            // 1.
            StepOne_use_hi_taskinst_to_change_ru_task(sqlSession, currentTaskEntity, backToHistoricTaskInstanceEntity);

            // 2.
            StepTwo_change_ru_identitylink(sqlSession, currentTaskEntityId, backToHistoricTaskInstanceEntityId, backToAssignee);

            // 3.
            StepThree_change_ru_execution(sqlSession, executionId, processDefinitionId, backToTaskDefinitionKey);
            success = true;
        } catch (Exception e) {
            throw new ActivitiException("dbBackTo Exception", e);
        } finally {
            if (success) {
                sqlSession.commit();
                result = DbTaskReturnService.I_DONE;
            } else {
                sqlSession.rollback();
                result = DbTaskReturnService.I_ROLLBACK;
            }
            sqlSession.close();
        }

        return result;

    }


    private void StepThree_change_ru_execution(SqlSession sqlSession,
                                               String executionId, String processDefinitionId,
                                               String backToTaskDefinitionKey) throws Exception {
        List<ExecutionEntity> currentExecutionEntityList = sqlSession.selectList("selectExecution", executionId);

        if (currentExecutionEntityList.size() > 0) {
            ActivityImpl activity = getActivitiImp(processDefinitionId, backToTaskDefinitionKey);
            Iterator<ExecutionEntity> execution = currentExecutionEntityList.iterator();

            while (execution.hasNext()) {
                ExecutionEntity e = execution.next();
                e.setActivity(activity);
                p(sqlSession.update("updateExecution", e));
            }
        }
    }


    private void StepTwo_change_ru_identitylink(SqlSession sqlSession,
                                                       String currentTaskEntityId,
                                                       String backToHistoricTaskInstanceEntityId,
                                                       String backToAssignee) throws Exception {

        ListQueryParameterObject para = new ListQueryParameterObject();
        para.setParameter(currentTaskEntityId);
        List<IdentityLinkEntity> currentTaskIdentityLinkEntityList = sqlSession.selectList("selectIdentityLinksByTask", para);


        if (currentTaskIdentityLinkEntityList.size() > 0) {
            Iterator<IdentityLinkEntity> identityLinkEntityList = currentTaskIdentityLinkEntityList.iterator();
            IdentityLinkEntity identityLinkEntity;
            TaskEntity tmpTaskEntity;
            tmpTaskEntity = new TaskEntity();
            tmpTaskEntity.setId(backToHistoricTaskInstanceEntityId);

            while (identityLinkEntityList.hasNext()) {
                identityLinkEntity = identityLinkEntityList.next();
                identityLinkEntity.setTask(tmpTaskEntity);
                identityLinkEntity.setUserId(backToAssignee);
                Map<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("id", identityLinkEntity.getId());
                sqlSession.delete("deleteIdentityLink", parameters);
                sqlSession.insert("insertIdentityLink", identityLinkEntity);
            }
        }
    }


    private void StepOne_use_hi_taskinst_to_change_ru_task(SqlSession sqlSession,
                                                                  TaskEntity currentTaskEntity,
                                                                  HistoricTaskInstanceEntity backToHistoricTaskInstanceEntity) throws Exception {

        sqlSession.delete("deleteTask", currentTaskEntity);
        currentTaskEntity.setName(backToHistoricTaskInstanceEntity.getName());
        currentTaskEntity.setTaskDefinitionKey(backToHistoricTaskInstanceEntity.getTaskDefinitionKey());
        currentTaskEntity.setId(UUIDUtil.getUUID());
        sqlSession.insert("insertTask", currentTaskEntity);
    }


    public void p(Object o) {
        System.out.println(o);
    }


    private ActivityImpl getActivitiImp(String processDefinitionId,
                                        String taskDefinitionKey) {
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processDefinitionId);
        List<ActivityImpl> activitiList = processDefinition.getActivities();

        boolean b;
        Object activityId;
        for (ActivityImpl activity : activitiList) {
            activityId = activity.getId();
            b = activityId.toString().equals(taskDefinitionKey);
            if (b) {
                return activity;
            }
        }
        return null;
    }


    private TaskEntity getCurrentTaskEntity(String id) {
        return (TaskEntity) taskService.createTaskQuery().taskId(id).singleResult();
    }


    private HistoricTaskInstanceEntity getHistoryTaskEntity(String id) {
        return (HistoricTaskInstanceEntity) historyService.createHistoricTaskInstanceQuery().taskId(id).singleResult();
    }


    private SqlSession getSqlSession() {
        ProcessEngineImpl processEngine = (ProcessEngineImpl) ProcessEngines.getDefaultProcessEngine();
        DbSqlSessionFactory dbSqlSessionFactory = (DbSqlSessionFactory) processEngine
                .getProcessEngineConfiguration().getSessionFactories()
                .get(DbSqlSession.class);

        SqlSessionFactory sqlSessionFactory = dbSqlSessionFactory.getSqlSessionFactory();
        return sqlSessionFactory.openSession();
    }
}
