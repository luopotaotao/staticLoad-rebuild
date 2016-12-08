package com.tt.web.controller.project;//package tt.controller.project;
//
//import com.alibaba.fastjson.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import com.tt.web.controller.BaseController;
//import com.tt.model.Project;
//import com.tt.service.ProjectServiceI;
//
//import java.util.Arrays;
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * Created by taotao on 2016/9/27.
// */
//@Controller
//@RequestMapping("project/manage")
//public class ProjectController extends BaseController<Project> {
//    @RequestMapping("index")
//    public String index(@RequestParam(name = "project_id",required = false) String project_id,Model model){
//        model.addAttribute("project_id",project_id!=null?project_id:"null");
//        return "module_project/index";
//    }
//}
