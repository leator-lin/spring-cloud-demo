package com.define.commons.controller;

import com.define.commons.utils.R;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 创建模型
     */
    @RequestMapping("/create")
    public void create(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 初始化一个空模型
            Model model = repositoryService.newModel();

            // 设置一些默认信息
            String name = "new-process";
            String description = "";
            int revision = 1;
            String key = "process";

            ObjectNode modelNode = objectMapper.createObjectNode();
            modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

            model.setName(name);
            model.setKey(key);
            model.setMetaInfo(modelNode.toString());

            repositoryService.saveModel(model);
            String id = model.getId();

            // 完善ModelEditorSource
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
            response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + model.getId());
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
        */

    /**
     * 5：    (1)使用流程变量设置字符串（格式：LeaveBill.id的形式），通过设置，让启动的流程（流程实例）关联业务
     * (2)使用正在执行对象表中的一个字段BUSINESS_KEY（Activiti提供的一个字段），让启动的流程（流程实例）关联业务
     *//*
        //格式：LeaveBill.id的形式（使用流程变量）
        String objId = key+"."+id;
        variables.put("objId", objId);
        //6：使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
        runtimeService.startProcessInstanceByKey(key,objId,variables);

    }*/
    public String getBusinessObjId(String taskId) {
        //1  获取任务对象
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        //2  通过任务对象获取流程实例
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        //3 通过流程实例获取“业务键”
        String businessKey = pi.getBusinessKey();
        //4 拆分业务键，拆分成“业务对象名称”和“业务对象ID”的数组
        // a=b  LeaveBill.1
        String objId = null;
        if (StringUtils.isNotBlank(businessKey)) {
            objId = businessKey.split("\\.")[1];
        }
        return objId;
    }


    /**
     * @userFor :获得流程的变量信息  taskService.complete(taskid,variables);
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
                    .hasNext(); ) {
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

    @GetMapping("/getProcDefIdAndNameByKey/{keyName}")
    public Map<String, String> getProcDefIdAndNameByKey(@PathVariable String keyName) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().
                processDefinitionKey(keyName).orderByProcessDefinitionVersion().desc().list().get(0);
        Map<String, String> map = new HashMap<>();
        map.put("procDefId", processDefinition.getId());
        map.put("name", processDefinition.getName());
        return map;
    }

    @GetMapping("/deployFlow/{modelId}")
    public R deployFlow(@PathVariable String modelId) throws Exception {
        // 获取模型
        Model modelData = repositoryService.getModel(modelId);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

        if (bytes == null) {
            return R.ok("部署失败");
        }

        JsonNode modelNode = new ObjectMapper().readTree(bytes);

        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if (model.getProcesses().size() == 0) {
            return R.error("数据模型不符要求，请至少设计一条主线流程");
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

        // 发布流程
        String processName = modelData.getName() + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes, "UTF-8")).deploy();
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);
        return R.ok("部署成功");
    }

    //查询任务节点有多少个分支
    private List<SequenceFlow> getTaskBranch(String procDefId, String taskDefId) {
        BpmnModel model = repositoryService.getBpmnModel(procDefId);
        List<SequenceFlow> branchList = new ArrayList<>();
        if (model != null) {
            Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
            for (FlowElement e : flowElements) {
                if (e.getClass().toString().endsWith("SequenceFlow")) {
                    SequenceFlow sf = (SequenceFlow) e;
                    if (sf.getSourceRef().equals(taskDefId)) {
                        branchList.add(sf);
                    }
                }
            }
        }
        return branchList;
    }

    /**
     * 根据流程实例id获取流程运行任务id集合
     */
    @GetMapping("/getTaskListByProcInsId")
    public List<String> getTaskListByProcInsId(String procInstId) {
        //创建查询对象
        TaskQuery taskQuery = taskService.createTaskQuery();
        //指定流程实例id，只查询某个流程的任务
        taskQuery.processInstanceId(procInstId);
        //获取查询列表
        List<Task> taskList = taskQuery.list();
        List<String> taskIdList = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) {
            taskIdList.add(taskList.get(i).getId());
        }
        return taskIdList;
    }

    // 启动流程
    @GetMapping("/startFlow")
    public R startFlow(String procDefKey) throws Exception {
        Map<String, Object> variables = new HashMap<>();
        variables.put("user1", "窗口收件员5,窗口收件员51,窗口收件员52");
        variables.put("user2", "业务受理员5");
        variables.put("user3", "业务审批员5");
        variables.put("user4", "窗口发件员5");
        ProcessInstance procIns = runtimeService.startProcessInstanceByKey(procDefKey, variables);
        if (ObjectUtils.isEmpty(procIns)) {
            return R.error();
        }
        return R.ok();
    }

    // 任务办理
    @GetMapping("/complete")
    public R complete(String procInstId) throws Exception {
        if (StringUtils.isEmpty(procInstId)) {
            return R.error();
        }
        String taskId = getTaskListByProcInsId(procInstId).get(0);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        // 添加批注信息
        String processInstanceId = task.getProcessInstanceId();
        String opinion = "接件成功";
        taskService.addComment(taskId, processInstanceId, "UTF-8", opinion);
        taskService.complete(taskId);
        return R.ok("任务办理成功");
    }

    @GetMapping("/findMyTaskList")
    public String findMyTaskList(String userId) {
        List<Task> list = taskService
                .createTaskQuery().taskCandidateUser(userId).list();
        return list.get(0).getName();
    }
}