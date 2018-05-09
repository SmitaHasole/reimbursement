package com.controller;

import com.persistence.entity.UserData;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * created by Smita Hasole on 24-04-2018
 */

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class LoginController {

    private AuthenticationManager authenticationManager;
    UserService userService;
    UserDetailsService userDetailsService;


    @Autowired
    public LoginController(UserService userService, UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }



//    @RequestMapping("/login")
//    User createUser(User user){
//        User user1= new User();
//        try {
//            user1 = userService.registerNewUserAccount(user);
//        } catch (EmailExistsException e) {
//            e.printStackTrace();
//        }
//        return user1;
//    }

//    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
//    public ModelAndView login() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("login");
//        return modelAndView;
//    }


//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public ModelAndView registration() {
//        ModelAndView modelAndView = new ModelAndView();
//        UserData user = new UserData();
//        modelAndView.addObject("user", user);
//        modelAndView.setViewName("registration");
//        return modelAndView;
//    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public UserData createNewUser(@RequestBody UserData user) {
        ModelAndView modelAndView = new ModelAndView();
        UserData userExists = userService.getUserByEmail(user.getEmailId());
        try {
            userService.registerNewUserAccount(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new UserData());
            modelAndView.setViewName("registration");
        } catch (EmailExistsException e) {
            e.printStackTrace();
        }
        return userExists;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) throws AuthenticationException {

//        UserData userExists = userService.getUserByEmail(user.getEmailId());
//
//        final Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginUser.getEmailId(), loginUser.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String roles = Role.Requester.name();
//        for (GrantedAuthority auth : authentication.getAuthorities()) {
//            roles = auth.getAuthority();
//        }
        UserDetails user = userDetailsService.loadUserByUsername(username);
        if (user != null) {
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            if (bcrypt.matches(password, user.getPassword())) {
                return ResponseEntity.ok(user);
            }

        }
        return (ResponseEntity<?>) ResponseEntity.noContent();
    }
}
