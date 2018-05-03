package com.controller;

import com.persistence.ResponseBean;
import com.persistence.entity.Reimbursement;
import com.persistence.entity.UserData;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * created by Smita Hasole on 23-04-2018
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/user")
public class UserController {

    UserService userService;
    UserDetailsService userDetailsService;
    @Autowired
    public UserController(UserService userService, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    UserData createUser(@RequestBody UserData userData) {
        UserData user1 = new UserData();
        try {
            System.out.println("heyyyyyyyyy:" + userService);
            user1 = userService.registerNewUserAccount(userData);
        } catch (EmailExistsException e) {
            e.printStackTrace();
        }
        return user1;
    }
//this Api will get called when users's reimbursement data is saved
    @PostMapping("/update")
    ResponseEntity<ResponseBean> updateUser(@RequestBody UserData userData) {
        UserData updateUserData = userService.updateUserData(userData);
        ResponseBean responseBean = new ResponseBean();
        if (updateUserData != null) {
            responseBean.setData(updateUserData);
            responseBean.setMessage("data saved successfully");
            responseBean.setStatus(HttpStatus.CREATED.toString());
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        }
        responseBean.setMessage("data not found");
        responseBean.setStatus(HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBean);
    }

    @GetMapping("/all")
    ResponseEntity<ResponseBean> getAllUsers(){
        List<UserData> userDataList = userService.getAllUsers();
        ResponseBean responseBean = new ResponseBean();
        if (userDataList != null && userDataList.size()!=0) {
            responseBean.setData(userDataList);
            responseBean.setMessage("data saved successfully");
            responseBean.setStatus(HttpStatus.CREATED.toString());
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        }
        responseBean.setMessage("data not found");
        responseBean.setStatus(HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBean);
    }

    @PostMapping("/{id}")
    ResponseEntity<ResponseBean> updateUser(@PathVariable("id") Long id){
     UserData userData = userService.getUserById(id);
        ResponseBean responseBean = new ResponseBean();
        if (userData != null) {
            responseBean.setData(userData);
            responseBean.setMessage("data saved successfully");
            responseBean.setStatus(HttpStatus.CREATED.toString());
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        }
        responseBean.setMessage("data not found");
        responseBean.setStatus(HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBean);
    }
}
