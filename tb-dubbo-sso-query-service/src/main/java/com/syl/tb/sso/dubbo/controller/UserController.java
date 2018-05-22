package com.syl.tb.sso.dubbo.controller;

import com.sty.tb.dubbo.pojo.User;
import com.sty.tb.dubbo.service.UserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("user")
@Controller
public class UserController {

    private static final String TB_TOKEN = "TB_TOKEN";

    @Autowired
    private UserQueryService userQueryService;

    @RequestMapping(value = "{token}",method = RequestMethod.GET)
    public ResponseEntity<User> quertUserByToken(@PathVariable("token")String token){
        try {
            User user = userQueryService.queryUserByToken(token);
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