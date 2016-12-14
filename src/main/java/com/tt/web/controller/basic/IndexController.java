package com.tt.web.controller.basic;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by taotao on 2016/9/27.
 */
@RequestMapping("basic/index")
@Controller
public class IndexController {
    @RequestMapping("index")
    public String companyIndex(@RequestParam(value = "selectDept",required = false) Boolean selectDept, Model model){
        model.addAttribute("selectDept",selectDept!=null?selectDept:false);
        return "module_basic/index";
    }
}
