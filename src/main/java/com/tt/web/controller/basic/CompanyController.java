package com.tt.web.controller.basic;

import com.alibaba.fastjson.JSONObject;
import com.tt.annotation.NeedDept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.tt.web.controller.BaseController;
import com.tt.model.Company;
import com.tt.service.CompanyServiceI;
import com.tt.util.UrlStringDecoder;

import java.util.*;

/**
 * Created by taotao on 2016/9/27.
 */
@Controller
@RequestMapping("basic/company")
public class CompanyController extends BaseController<Company> {

    @Autowired
    private CompanyServiceI companyService;

    @NeedDept
    @RequestMapping("index")
    public String index(Model model) {
        return "module_basic/company";
    }

    @RequestMapping("partial")
    public String partial(@RequestParam(name = "typ") Byte type, Model model) {
        if (type != null) {
            model.addAttribute("typ", type);
        }
        return "module_basic/company_partial";
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Company get(@PathVariable int id) {
        return companyService.get(id);
    }

    @RequestMapping(value = "query", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject list(@RequestParam(required = false) Byte typ,
                           @RequestParam(required = false) String name,
                           @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                           @RequestParam(value = "rows", required = false, defaultValue = "10") Integer pageSize) {
        Map<String, Object> params = new HashMap<>();
        name = UrlStringDecoder.decode(name);
        if (name != null) {
            params.put("name", name);
        }
        if (typ != null) {
            params.put("typ", typ);
        }
        List<Company> list = null;
        long count = companyService.count(params);
        if (count > 0) {
            list = companyService.list(params, page, pageSize);
        }
        return listResponse(count, list);
    }

    @RequestMapping(value = "post", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject add(@ModelAttribute Company company) {
        companyService.add(company);
        return flagResponse(company.getId() > 0);
    }

    @RequestMapping(value = "put", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@ModelAttribute Company company) {
        companyService.update(company);
        return flagResponse(company.getId() > 0);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(@RequestParam(value = "ids[]") int[] ids) {
        List<Integer> list = new LinkedList<>();
        Arrays.stream(ids).forEach(id -> list.add(id));
        int ret = companyService.del(list);
        return flagResponse(ret);
    }
}
