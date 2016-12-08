package com.tt.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tt on 2016/12/8.
 */
@Controller
public class MainController {
    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String index(){
        return "main/index";
    }
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String login(@RequestParam(required = false) String info){
        return "main/admin_login";
    }
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @RequestMapping("authimg")
    public void kaptcha(){

    }

    @RequestMapping("/welcome")
    public String welcome(){
        return "main/welcome";
    }
}
