package com.tt.web.controller.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by taotao on 2016/9/27.
 */
@RequestMapping("basic/index")
@Controller
public class IndexController {
    @RequestMapping("index")
    public String companyIndex(){
        return "module_basic/index";
    }
}
