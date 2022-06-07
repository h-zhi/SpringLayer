//package com.example.layer.sys.controller.test;
//
//import org.flowable.task.api.Task;
//import org.flowable.engine.RuntimeService;
//import org.flowable.engine.TaskService;
//import org.springframework.http.MediaType;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @Author Hzhi
// * @Date 2022-05-07 9:43
// * @description
// **/
//@RestController
//@RequestMapping(value = "/test/flowable")
//public class FlowableTestController {
//
//    @Resource
//    private RuntimeService runtimeService;
//
//    @Resource
//    private TaskService taskService;
//
//    @RequestMapping(value="/process", method= RequestMethod.POST)
//    public void startProcessInstance() {
//        this.startProcess();
//    }
//
//    @RequestMapping(value="/tasks", method= RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
//    public List<TaskRepresentation> queryTasks(@RequestParam String assignee) {
//        List<Task> tasks = this.getTasks(assignee);
//        List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
//        for (Task task : tasks) {
//            dtos.add(new TaskRepresentation(task.getId(), task.getName()));
//        }
//        return dtos;
//    }
//
//    @Transactional
//    public void startProcess() {
//        runtimeService.startProcessInstanceByKey("oneTaskProcess");
//    }
//
//    @Transactional
//    public List<Task> getTasks(String assignee) {
//        return taskService.createTaskQuery().taskAssignee(assignee).list();
//    }
//
//    static class TaskRepresentation {
//
//        private String id;
//        private String name;
//
//        public TaskRepresentation(String id, String name) {
//            this.id = id;
//            this.name = name;
//        }
//
//        public String getId() {
//            return id;
//        }
//        public void setId(String id) {
//            this.id = id;
//        }
//        public String getName() {
//            return name;
//        }
//        public void setName(String name) {
//            this.name = name;
//        }
//
//    }
//
//}