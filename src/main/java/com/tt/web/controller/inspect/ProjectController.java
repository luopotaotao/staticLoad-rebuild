package com.tt.web.controller.inspect;//package tt.controller.inspect;
//
//import com.alibaba.fastjson.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import com.tt.web.controller.BaseController;
//import com.tt.model.InspectProject;
//import com.tt.service.InspectProjectServiceI;
//import com.tt.util.UrlStringDecoder;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.util.*;
//
///**
// * Created by tt on 2016/10/2.
// */
//@Controller
//@RequestMapping("moduleInspectProjectController")
//public class ProjectController extends BaseController<InspectProject> {
//    @Autowired
//    private InspectProjectServiceI inspectProjectService;
//
//    @RequestMapping("index")
//    public String index(Model model){
//        model.addAttribute("baseUrl","/moduleBasicInspectProjectController");
//        return "module_inspect/inspectProject";
//    }
//
//    @RequestMapping("partial")
//    public String partial(Model model){
//        model.addAttribute("baseUrl","/moduleBasicInspectProjectController");
//        return "module_basic/company_partial";
//    }
//
//    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public InspectProject get(@PathVariable int id) {
//        return inspectProjectService.get(id);
//    }
//
//    @RequestMapping(value = "query",method = RequestMethod.GET)
//    @ResponseBody
//    public JSONObject list(@RequestParam(required = false) Byte typ,
//                           @RequestParam(required = false) String name,
//                           @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
//                           @RequestParam(value = "rows",required = false,defaultValue = "10") Integer pageSize) {
//        Map<String,Object> params = new HashMap<>();
//        name = UrlStringDecoder.decode(name);
//        if (name!=null) {
//            params.put("name",name);
//        }
//        List<InspectProject> list = inspectProjectService.list(params,page,pageSize);
//        long count = inspectProjectService.count(params);
//        return listResponse(count, list);
//    }
//
//    //    @RequestMapping(value = "post", method = RequestMethod.POST)
//    @RequestMapping(value = "post")
//    @ResponseBody
//    public JSONObject add(@ModelAttribute InspectProject company) {
//        inspectProjectService.add(company);
//        return flagResponse(company.getId()>0);
//    }
//
//    @RequestMapping(value = "put", method = RequestMethod.POST)
//    @ResponseBody
//    public JSONObject update(@ModelAttribute InspectProject company) {
//        inspectProjectService.update(company);
//        return flagResponse(company.getId()>0);
//    }
//
//    @RequestMapping(value = "delete", method = RequestMethod.POST)
//    @ResponseBody
//    public JSONObject delete(@RequestParam(value = "ids[]") int[] ids) {
//        List<Integer> list = new LinkedList<>();
//        Arrays.stream(ids).forEach(id->list.add(id));
//        int ret = inspectProjectService.del(list);
//        return flagResponse(ret);
//    }
//}
