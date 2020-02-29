package com.define.controller;
import com.define.utils.R;
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
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    ManagementService managementService;

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
        return R.ok(procIns.getId());
    }

    @GetMapping("/complete")
    public R complete(String taskId, String opinion, String result, String message) {

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Map<String, Object> variables = new HashMap<>();

        variables.put("taskId", taskId);
        variables.put("opinion", opinion);
        variables.put("result", result);
        variables.put("message", message);

        // 添加批注信息
        String processInstanceId = task.getProcessInstanceId();
        taskService.addComment(taskId, processInstanceId, result, opinion);
        task.setDescription(opinion);
        taskService.complete(taskId, variables);
        return R.ok("任务办理成功");
    }

    @GetMapping("/findMyTaskList")
    public String findMyTaskList(String userId) {
        List<Task> list = taskService
                .createTaskQuery().taskCandidateUser(userId).list();
        return list.get(0).getName();
    }

    @GetMapping("/deleteDeployment")
    public void deleteDeployment(String deployId) {
        repositoryService.deleteDeployment(deployId);
    }

    @GetMapping("/getTaskList")
    public R getTaskList(String procInsId) {
        R r = new R();
        TaskQuery taskQuery = taskService.createTaskQuery();
        //指定流程实例id，只查询某个流程的任务
        taskQuery.processInstanceId(procInsId);
        //获取查询列表
        return r.put("taskList", taskQuery.list());
    }

    @GetMapping("/getTableName")
    public R getTableName(String className) {
        R r = new R();
        String tableName = managementService.getTableName(Task.class);
        return r.put("tableName", tableName);
    }

    @GetMapping(value = "/findHisTaskByTaskId", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public R findHisTaskByTaskId(String taskId){
        R r = new R();
        HistoricTaskInstance hisTask = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        return r.put("hisTask", hisTask);
    }
}