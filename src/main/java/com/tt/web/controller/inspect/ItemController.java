package com.tt.web.controller.inspect;//package tt.controller.inspect;
//
//import com.alibaba.fastjson.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import com.tt.web.controller.BaseController;
//import com.tt.model.InspectItem;
//import com.tt.service.InspectItemServiceI;
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
//@RequestMapping("moduleInspectItemController")
//public class ItemController extends BaseController<InspectItem> {
//    @Autowired
//    private InspectItemServiceI inspectItemService;
//
//    @RequestMapping("index")
//    public String index(Model model) {
//        model.addAttribute("baseUrl", "/basic/inspectItem");
//        return "module_inspect/inspectItem";
//    }
//
//    @RequestMapping("partial")
//    public String partial(Model model) {
//        model.addAttribute("baseUrl", "/basic/inspectItem");
//        return "module_basic/company_partial";
//    }
//
//    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public InspectItem get(@PathVariable int id) {
//        return inspectItemService.get(id);
//    }
//
//    @RequestMapping(value = "query", method = RequestMethod.GET)
//    @ResponseBody
//    public JSONObject list(@RequestParam(required = false) String name,
//                           @RequestParam(value = "page", required = false) Integer page,
//                           @RequestParam(value = "rows", required = false) Integer pageSize) {
//        Map<String,Object> params = new HashMap<>();
//        name = UrlStringDecoder.decode(name);
//        if (name!=null) {
//            params.put("name",name);
//        }
//        List<InspectItem> list = inspectItemService.list(params, page, pageSize);
//        long count = inspectItemService.count(params);
//        return listResponse(count, list);
//    }
//    @RequestMapping(value = "comboList", method = RequestMethod.GET)
//    @ResponseBody
//    public List<InspectItem> comboList(@RequestParam(required = false) String name) {
//        Map<String,Object> params = createHashMap("name",name);
//        List<InspectItem> list = inspectItemService.list(params,null,null);
//        return list;
//    }
//
//    @RequestMapping(value = "post", method = RequestMethod.POST)
//    @ResponseBody
//    public JSONObject add(@ModelAttribute InspectItem company) {
//        inspectItemService.add(company);
//        return flagResponse(company.getId()>0);
//    }
//
//    @RequestMapping(value = "put", method = RequestMethod.POST)
//    @ResponseBody
//    public JSONObject update(@ModelAttribute InspectItem company) {
//        inspectItemService.update(company);
//        return flagResponse(company.getId()>0);
//    }
//
//    @RequestMapping(value = "delete", method = RequestMethod.POST)
//    @ResponseBody
//    public JSONObject delete(@RequestParam(value = "ids[]") int[] ids) {
//        List<Integer> list = new LinkedList<>();
//        Arrays.stream(ids).forEach(id -> list.add(id));
//        int ret = inspectItemService.del(list);
//        return flagResponse(ret);
//    }
//}
