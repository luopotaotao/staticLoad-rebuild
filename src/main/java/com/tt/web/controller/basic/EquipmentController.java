package com.tt.web.controller.basic;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.tt.web.controller.BaseController;
import com.tt.model.Equipment;
import com.tt.service.EquipmentServiceI;
import com.tt.util.UrlStringDecoder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by tt on 2016/10/2.
 */
@Controller
@RequestMapping("basic/equipment")
public class EquipmentController extends BaseController<Equipment> {
    @Autowired
    private EquipmentServiceI equipmentService;

    @RequestMapping("index/{dept_id}")
    public String index(){
        return "module_basic/dept_equipment";
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Equipment get(@PathVariable int id) {
        return equipmentService.get(id);
    }

    @RequestMapping(value = "query",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject list(@RequestParam(required = false) String name) {
        name = UrlStringDecoder.decode(name);
        Map<String,Object> params = createHashMap();
        if (name!=null) {
            params.put("name",name);
        }
        List<Equipment> list = equipmentService.list(params,null,null);
        return listResponse(list);
    }

    //    @RequestMapping(value = "post", method = RequestMethod.POST)
    @RequestMapping(value = "post")
    @ResponseBody
    public JSONObject add(@ModelAttribute Equipment equipment, BindingResult result) {
        equipmentService.add(equipment);
        return flagResponse(equipment.getId()>0);
    }

    @RequestMapping(value = "put", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@ModelAttribute Equipment equipment) {
        equipmentService.update(equipment);
        return flagResponse(equipment.getId()>0);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(@RequestParam(value = "ids[]") int[] ids) {
        List<Integer> list = new LinkedList<>();
        Arrays.stream(ids).forEach(id->list.add(id));
        int ret = equipmentService.del(list);
        return flagResponse(ret);
    }
}
