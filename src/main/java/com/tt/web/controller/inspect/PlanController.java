package com.tt.web.controller.inspect;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.tt.web.controller.BaseController;
import com.tt.model.InspectPlan;
import com.tt.service.InspectPlanServiceI;
import com.tt.util.UrlStringDecoder;

import java.util.*;

/**
 * Created by tt on 2016/10/2.
 */
@Controller
@RequestMapping("inspect/plan")
public class PlanController extends BaseController<InspectPlan> {
    @Autowired
    private InspectPlanServiceI inspectPlanService;

    @RequestMapping("index")
    public String index(Model model) {
        return "module_data/plan";
    }

    @RequestMapping("selectScheme")
    public String selectScheme(Model model) {
        return "module_data/plan_select_scheme";
    }

    @RequestMapping("selectUser")
    public String selectUser( Model model) {
        return "module_data/plan_select_user";
    }

    @RequestMapping("selectEquipment/{dept_id}")
    public String selectEquipment(@PathVariable Integer dept_id, Model model) {
        model.addAttribute("dept_id", dept_id);
        return "module_data/plan_select_equipment";
    }

    @RequestMapping("showData/{prg}/{plan_id}")
    public String showData(@PathVariable String prg, @PathVariable Integer plan_id, Model model) {

        InspectPlan plan = inspectPlanService.get(plan_id);
        model.addAttribute("prg", prg);
        model.addAttribute("stzh", plan.getStzh());
        return "module_data/plan_show_data";
    }

    @RequestMapping("selectData/{plan_id}")
    public String selectData(@PathVariable Integer plan_id, Model model) {
        model.addAttribute("plan_id", plan_id);
        return "module_data/plan_select_data";
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public InspectPlan get(@PathVariable int id) {
        return inspectPlanService.get(id);
    }

    @RequestMapping(value = "query", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject list(@RequestParam(required = false) String name,
                           @RequestParam(value = "page", required = false) Integer page,
                           @RequestParam(value = "rows", required = false) Integer pageSize) {
        Map<String, Object> params = new HashMap<>();
        name = UrlStringDecoder.decode(name);
        if (name != null) {
            params.put("name", name);
        }
        List<InspectPlan> list = inspectPlanService.list(params, page, pageSize);
        long count = inspectPlanService.count(params);
        return listResponse(count, list);
    }

    @RequestMapping(value = "queryBySchemeId",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject queryBySchemeId(@RequestParam(value = "id") Integer id){
        Map<String,Object> params = new HashMap<>();
        params.put("inspectScheme.id",id);
        long count = inspectPlanService.count(params);
        List<InspectPlan> list;
        if(count>0){
            list = inspectPlanService.list(params);
        }else{
            list = new LinkedList<>();
        }
        return listResponse(count, list);
    }

    @RequestMapping(value = "post", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject add(@ModelAttribute InspectPlan plan, BindingResult result) {
        inspectPlanService.add(plan);
        return flagResponse(plan.getId() > 0);
    }

    @RequestMapping(value = "put", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@ModelAttribute InspectPlan plan) {
        inspectPlanService.update(plan);
        return flagResponse(plan.getId() > 0);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(@RequestParam(value = "ids[]") int[] ids) {
        List<Integer> list = new LinkedList<>();
        Arrays.stream(ids).forEach(id -> list.add(id));
        int ret = inspectPlanService.del(list);
        return flagResponse(ret);
    }
}
