package com.syl.tb.usermanage.web;

import com.syl.tb.usermanage.dto.Result;
import com.syl.tb.usermanage.pojo.User;
import com.syl.tb.usermanage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    @ResponseBody
    public Result queryUserList(@RequestParam("page")Integer page,@RequestParam("rows") Integer rows){
        Result result = userService.queryUserList(page,rows);
        return result;
    }
    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public ResponseEntity<User> queryUserById(@PathVariable("id")Long id){
        try {
            User user = userService.queryUserById(id);
            if (user == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> queryUserById(User user){
        boolean b = userService.saveUser(user);
        if (b){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateUser(User user){
        boolean b = userService.updateUser(user);
        if (b){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@RequestParam(value = "id",defaultValue = "-1")Long id){
        if (id == -1)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        boolean b = userService.deleteUser(id);
        if (b){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
