package com.syl.tb.sso.controller;

import com.syl.tb.common.utils.CookieUtils;
import com.syl.tb.sso.mapper.UserMapper;
import com.syl.tb.sso.pojo.User;
import com.syl.tb.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("user")
@Controller
public class UserController {

    private static final String TB_TOKEN = "TB_TOKEN";

    @Autowired
    private UserService userService;

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
    @RequestMapping(value = "{param}/{type}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> check(@PathVariable("param") String param,
                                         @PathVariable("type") Integer type) {
        try{
            Boolean bool = userService.check(param,type);
            if (bool == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.ok(!bool);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value = "doRegister",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doRegister(@Valid User user, BindingResult bindingResult){
        Map<String,Object> map = new HashMap<>();
        if (bindingResult.hasErrors()){
            map.put("status",400);
            List<ObjectError> list = bindingResult.getAllErrors();
            List<String> l = new ArrayList<>();
            for (ObjectError item:list) {
                l.add(item.getDefaultMessage().toString());
            }
            map.put("data","参数有误!"+ StringUtils.join(l,"|"));
            return map;
        }

       try {
           Boolean bool = userService.doRegister(user);
           if (bool){
               map.put("status",200);
           }else {
               map.put("status",500);
               map.put("data","哈哈----");
           }
       }catch (Exception e){
           e.printStackTrace();
           map.put("status",500);
           map.put("data","哈哈----");
       }
        return map;
    }


    @RequestMapping(value = "doLogin",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doLogin(User user, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> result = new HashMap<>();
        System.out.println(user);
        try {
            String token = userService.doLogin(user.getUsername(),user.getPassword());
            if (StringUtils.isEmpty(token)){
                result.put("status",500);
                return result;
            }

            CookieUtils.setCookie(request,response,TB_TOKEN,token);

            result.put("status",200);
        }catch (Exception e){
            e.printStackTrace();
            result.put("status",500);
            result.put("data","登录失败");
        }
        return result;
    }
    @RequestMapping(value = "{token}",method = RequestMethod.GET)
    public ResponseEntity<User> quertUserByToken(@PathVariable("token")String token){
        try {
            User user = userService.quertUserByToken(token);
            System.out.println(user);
            if (user == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            return ResponseEntity.ok(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}