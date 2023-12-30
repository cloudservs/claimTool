package com.cloudservs.claimtool.restController;

import com.cloudservs.claimtool.domain.ToolUser;
import com.cloudservs.claimtool.handler.UserHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserRestService {

    @Autowired
    UserHandler userHandler;
    @PostMapping("/signup")
    public String registerUser(@RequestBody ToolUser user) {
        return userHandler.registerUser(user);
    }
    @PostMapping("/signIn")
    public Object singIn(@RequestBody ToolUser user, HttpServletRequest request) {
        return userHandler.singInUser(user,request);
    }
    @PostMapping("/cpwd")
    public Object changePassword(@RequestBody ToolUser user, HttpServletRequest request) {
        return userHandler.singInUser(user,request);
    }
    @PostMapping("/rpwd")
    public Object resetPassword(@RequestBody ToolUser user, HttpServletRequest request) {
        return userHandler.singInUser(user,request);
    }

}
