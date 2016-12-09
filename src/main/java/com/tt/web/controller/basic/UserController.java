package com.tt.web.controller.basic;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.tt.web.controller.BaseController;
import com.tt.model.User;
import com.tt.service.UserServiceI;
import com.tt.util.UrlStringDecoder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tt on 2016/10/2.
 */
@Controller
@RequestMapping("basic/user")
public class UserController extends BaseController<User> {
    @Autowired
    private UserServiceI userService;

    @RequestMapping("index/{dept_id}")
    public String index(@PathVariable Integer dept_id, Model model) {
        model.addAttribute("dept_id", dept_id);
        return "module_basic/dept_users";
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User get(@PathVariable int id) {
        return userService.get(id);
    }

//    @RequestMapping(value = "{dept_id}/query", method = RequestMethod.GET)
//    @ResponseBody
//    public JSONObject list(@PathVariable Integer dept_id,
//                           @RequestParam(required = false) String name) {
//
//        name = UrlStringDecoder.decode(name);
//        Map<String, Object> params = createHashMap("dept_id", dept_id);
//        params.put("name", name);
//        List<User> list = userService.list(params, null, null);
//        return listResponse(list);
//    }

    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject list(@RequestParam(required = false) String name) {
        Map<String, Object> params = new HashMap<>();
//        params.put("role",getSessionInfo().getRole());
        name = UrlStringDecoder.decode(name);
        if (name != null) {
            params.put("name", name);
        }
        List<User> list = userService.list(params, null, null);
        return listResponse(list);
    }


    @RequestMapping(value = "post", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject add(@ModelAttribute User user, BindingResult result) {
        if (userService.isExist(user.getUsername())) {
            JSONObject ret = jsonResponse("flag", false);
            ret.put("msg", "exist");
            return ret;
        }

        userService.add(user);
        return flagResponse(1);

    }

    @RequestMapping(value = "put", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@ModelAttribute User user) {
        userService.update(user);
        return flagResponse(user.getId());
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(@RequestParam(value = "ids[]") Integer[] ids) {
        int ret = userService.del(Arrays.asList(ids));
        return flagResponse(ret);
    }

}
