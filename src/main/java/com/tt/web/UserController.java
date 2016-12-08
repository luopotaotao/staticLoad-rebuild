//package com.tt.web;
//
//import com.tt.model.TestUser;
//import com.tt.service.TestUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.List;
//
///**
// * Created by tt on 2016/11/21.
// */
//@Controller
//@RequestMapping("api/users")
//public class UserController {
//    @Autowired
//    private TestUserService userService;
//
//    public void setUserService(TestUserService userService) {
//        this.userService = userService;
//    }
//
//    @RequestMapping("/list")
//    @ResponseBody
//    public List<TestUser> list(@RequestParam(value = "page") Integer page,
//                               @RequestParam(value = "rows")Integer rows){
//        return userService.list(page,rows);
//    }
//    @RequestMapping("/list_page")
//    public String showUsersPage(Model model,
//                                @RequestParam(value = "page",defaultValue = "1") Integer page,
//                                @RequestParam(value = "rows",defaultValue = "20")Integer rows){
//        List<TestUser> users = userService.list(page,rows);
//        model.addAttribute("users",users);
//
//        return "users_list";
//    }
//}
