//package com.tt.web.security;
//
//import com.tt.service.TestUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpSession;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by tt on 2016/11/22.
// */
//@Controller
//@RequestMapping("/login")
//public class LoginController {
//    @Autowired
//    private TestUserService userService;
//    @RequestMapping(value = {"","/"},method = RequestMethod.GET)
//    public String login(HttpSession session){
//        session.setAttribute("user_name","zhangsan");
//        Map<String,Object> map = new HashMap<>();
//        userService.testMap(map);
//        return "login";
//    }
//    @RequestMapping(value = {"/logout"},method = RequestMethod.GET)
//    public String logout(){
//        return "login";
//    }
//}
