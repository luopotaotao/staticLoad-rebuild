package com.tt.web.controller.basic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tt.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.tt.model.Area;
import com.tt.service.AreaServiceI;

import java.util.List;
import java.util.Map;

/**
 * Created by taotao on 2016/9/27.
 */
@Controller
@RequestMapping("basic/area")
public class AreaController extends BaseController<Area> {
    @Autowired
    private AreaServiceI areaService;

    @RequestMapping("index")
    public String index(Model model){
        return "module_basic/area";
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Area get(@PathVariable int id) {
        return areaService.get(id);
    }

    @RequestMapping(value = "query/{id}",method = RequestMethod.GET)
    @ResponseBody
    public JSONArray list(@PathVariable Integer id) {
        JSONArray ret = new JSONArray();
        Area area = areaService.get(id);
        ret.add(area);

        return ret;
    }
    @RequestMapping(value = "root",method = RequestMethod.GET)
    @ResponseBody
    public JSONArray root() {
        JSONArray ret = new JSONArray();
        Area area = areaService.getRoot();
        ret.add(area);

        return ret;
    }
    @RequestMapping(value = "area/{pid}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> queryCity(@PathVariable Integer pid){
        return areaService.queryAreaByPid(pid);
    }

//    @RequestMapping(value = "post", method = RequestMethod.POST)
    @RequestMapping(value = "post")
    @ResponseBody
    public Area add(@ModelAttribute Area area) {
        return areaService.add(area);
    }

    @RequestMapping(value = "put", method = RequestMethod.POST)
    @ResponseBody
    public Area update(@ModelAttribute Area area) {
        return areaService.update(area);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(@PathVariable Integer id) {
        int ret = areaService.del(id);
        return flagResponse(ret);
    }
}
