package com.define.commons.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

/**
 * 可以通过访问http://localhost:8080/activiti/create设计一个流程
 *
 * @Author: Lea
 * @Date: 2018/11/17 11:34
 */
@RestController
@RequestMapping("/activiti")
public class ActivitiController {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    /**
     * 创建模型
     */
    @RequestMapping("/create")
    public void create(HttpServletRequest request, HttpServletResponse response) {
        try {
            ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

            RepositoryService repositoryService = processEngine.getRepositoryService();

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            Model modelData = repositoryService.newModel();

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, "hello1111");
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            String description = "hello1111";
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName("hello1111");
            modelData.setKey("12313123");

            //保存模型
            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
            response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + modelData.getId());
        } catch (Exception e) {
            System.out.println("创建模型失败：");
        }
    }

    /**更新请假状态，启动流程实例，让启动的流程实例关联业务*/
    /*public void saveStartProcess(WorkflowBean workflowBean) {
        //1：获取请假单ID，使用请假单ID，查询请假单的对象LeaveBill
        Long id = workflowBean.getId();
        LeaveBill leaveBill = leaveBillDao.findLeaveBillById(id);
        //2：更新请假单的请假状态从0变成1（初始录入-->审核中）
        leaveBill.setState(1);
        //3：使用当前对象获取到流程定义的key（对象的名称就是流程定义的key）
        String key = leaveBill.getClass().getSimpleName();
        *//**
         * 4：从Session中获取当前任务的办理人，使用流程变量设置下一个任务的办理人
         * inputUser是流程变量的名称，
         * 获取的办理人是流程变量的值
         *//*
        Map<String, Object> variables = new HashMap<String,Object>();
        variables.put("inputUser", SessionContext.get().getName());//表示惟一用户
        *//**
         * 5：    (1)使用流程变量设置字符串（格式：LeaveBill.id的形式），通过设置，让启动的流程（流程实例）关联业务
         (2)使用正在执行对象表中的一个字段BUSINESS_KEY（Activiti提供的一个字段），让启动的流程（流程实例）关联业务
         *//*
        //格式：LeaveBill.id的形式（使用流程变量）
        String objId = key+"."+id;
        variables.put("objId", objId);
        //6：使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
        runtimeService.startProcessInstanceByKey(key,objId,variables);

    }*/

    public String getBusinessObjId(String taskId) {
        //1  获取任务对象
        Task task  =  taskService.createTaskQuery().taskId(taskId).singleResult();

        //2  通过任务对象获取流程实例
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        //3 通过流程实例获取“业务键”
        String businessKey = pi.getBusinessKey();
        //4 拆分业务键，拆分成“业务对象名称”和“业务对象ID”的数组
        // a=b  LeaveBill.1
        String objId = null;
        if(StringUtils.isNotBlank(businessKey)){
            objId = businessKey.split("\\.")[1];
        }
        return objId;
    }


    /**
     *@userFor :获得流程的变量信息  taskService.complete(taskid,variables);
     */
    private String getWorkflowVariables(String processInstanceId, String activityInstanceId) {
        String result = "";
        List historicDetailList = historyService.createHistoricDetailQuery()
                .processInstanceId(processInstanceId).activityInstanceId(
                        activityInstanceId).list();
        if (historicDetailList != null && historicDetailList.size() > 0) {
            //执行任务时提的意见
            String comment = "";
            //下一个审批人
            String piStatus = "";
            for (Iterator iterator = historicDetailList.iterator(); iterator
                    .hasNext();) {
                HistoricDetail historicDetail = (HistoricDetail) iterator
                        .next();
                HistoricVariableUpdate variable = (HistoricVariableUpdate) historicDetail;
                if ("workflowComment".equals(variable
                        .getVariableName()))
                    comment = String.valueOf(variable.getValue());
                else if ("flowName".equals(variable
                        .getVariableName()))
                    piStatus = variable.getValue() != null ? String
                            .valueOf(variable.getValue()) : "";
            }

            if (!"".equals(piStatus)
                    && !"".equals(comment)
                    && !"撤回"
                    .equals(piStatus))
                result = (new StringBuilder(String.valueOf(piStatus))).append(":").append(comment).toString();
        }
        return result;
    }
}