package com.tt.web.controller.inspect;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.tt.web.controller.BaseController;
import com.tt.model.InspectScheme;
import com.tt.service.InspectSchemeServiceI;
import com.tt.util.UrlStringDecoder;

import java.util.*;

/**
 * Created by tt on 2016/10/2.
 */
@Controller
@RequestMapping("inspect/scheme")
public class SchemeController extends BaseController<InspectScheme> {
    @Autowired
    private InspectSchemeServiceI inspectSchemeService;

    @RequestMapping("index")
    public String index(Model model) {
        return "module_data/scheme";
    }

    @RequestMapping("selectProject")
    public String selectProject() {
        return "module_data/scheme_select_project";
    }

    @RequestMapping("selectDept")
    public String selectDept() {
        return "module_data/scheme_select_dept";
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public InspectScheme get(@PathVariable int id) {
        return inspectSchemeService.get(id);
    }

    @RequestMapping(value = "query", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject list(@RequestParam(required = false) String name,
                           @RequestParam(value = "page", required = false) Integer page,
                           @RequestParam(value = "rows", required = false) Integer pageSize) {
        Map<String,Object> params = new HashMap<>();
        name = UrlStringDecoder.decode(name);
        if (name!=null) {
            params.put("name",name);
        }
        List<InspectScheme> list = inspectSchemeService.list(params, page, pageSize);
        long count = inspectSchemeService.count(params);
        return listResponse(count, list);
    }

    @RequestMapping(value = "queryByProjectId",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject queryByProjectId(@RequestParam(value = "id") Integer id){
        Map<String,Object> params = new HashMap<>();
        params.put("project.id",id);
        long count = inspectSchemeService.count(params);
        List<InspectScheme> list;
        if(count>0){
            list = inspectSchemeService.list(params);
        }else{
            list = new LinkedList<>();
        }
        return listResponse(count, list);
    }

    //    @RequestMapping(value = "post", method = RequestMethod.POST)
    @RequestMapping(value = "post")
    @ResponseBody
    public JSONObject add(@ModelAttribute InspectScheme scheme) {
        inspectSchemeService.add(scheme);
        return flagResponse(scheme.getId()>0);
    }

    @RequestMapping(value = "put", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@ModelAttribute InspectScheme company) {
        inspectSchemeService.update(company);
        return flagResponse(company.getId()>0);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(@RequestParam(value = "ids[]") int[] ids) {
        List<Integer> list = new LinkedList<>();
        Arrays.stream(ids).forEach(id -> list.add(id));
        int ret = inspectSchemeService.del(list);
        return flagResponse(ret);
    }
}
