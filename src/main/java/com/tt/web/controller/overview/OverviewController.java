package com.tt.web.controller.overview;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tt.web.controller.BaseController;
import com.tt.model.Overview;
import com.tt.model.Project;
import com.tt.service.OverviewServiceI;
import com.tt.service.ProjectServiceI;

import java.util.List;

/**
 * Created by tt on 2016/10/1.
 */
@Controller
@RequestMapping("overview/main")
public class OverviewController extends BaseController<Overview>{

    @Autowired
    private OverviewServiceI overviewServiceI;
    @Autowired
    private ProjectServiceI projectServiceI;

    @RequestMapping("index")
    public String index(Model model, @RequestParam(value = "project_id",required = false) Integer id) {
        Project selectedProject = null;
        if(id!=null&&id>0){
            selectedProject = projectServiceI.get(id);
        }

        if(selectedProject!=null){
            JSONObject project = new JSONObject();
            project.put("name",selectedProject.getName());
            project.put("code",selectedProject.getCode());
            JSONObject city = new JSONObject();
            city.put("text",selectedProject.getCity().getText());
            project.put("city",city);
            project.put("id",selectedProject.getId());
            project.put("lat",selectedProject.getLat());
            project.put("lng",selectedProject.getLng());

            model.addAttribute("selectedProject",project);
        }else{
            model.addAttribute("selectedProject","null");
        }
        return "module_overview/index";
    }

    /**
     * 根据行政区划查询工程信息列表
     *
     * @param area_id
     * @return 工程信息列表
     */
    @RequestMapping("{area_id}/queryProjects")
    @ResponseBody
    public List<Project> queryProjects(@PathVariable Integer area_id) {
        List<Project> projects = projectServiceI.listByAreaId(area_id);
        return projects;
    }

    /**
     * 查询行政区划树,以及每个行政区划下的工程总数
     *
     * @return [{id:1,text:全国,count:100,children:[{}]}]
     */
    @RequestMapping("queryAll")
    @ResponseBody
    public List<Overview> queryOverviews() {
        List<Overview> ret = overviewServiceI.queryAll();
        return ret;
    }

}
