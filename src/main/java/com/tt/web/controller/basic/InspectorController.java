package com.tt.web.controller.basic;

import com.alibaba.fastjson.JSONObject;
import com.tt.model.Inspector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.tt.web.controller.BaseController;
import com.tt.service.InspectorServiceI;
import com.tt.util.UrlStringDecoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tt on 2016/10/2.
 */
@Controller
@RequestMapping("basic/inspector")
public class InspectorController extends BaseController<Inspector> {
    @Autowired
    private InspectorServiceI inspectorService;

    @RequestMapping("index")
    public String index() {
        return "module_basic/dept_users";
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Inspector get(@PathVariable int id) {
        return inspectorService.get(id);
    }


    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject list(@RequestParam(required = false) String name) {
        Map<String, Object> params = new HashMap<>();
//        params.put("role",getSessionInfo().getRole());
        name = UrlStringDecoder.decode(name);
        if (name != null) {
            params.put("name", name);
        }
        List<Inspector> list = inspectorService.list(params, null, null);
        return listResponse(list);
    }


}
