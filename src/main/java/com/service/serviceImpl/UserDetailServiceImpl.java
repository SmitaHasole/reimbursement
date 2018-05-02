package com.service.serviceImpl;

import com.persistence.entity.Role;
import com.persistence.entity.UserData;
import com.persistence.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * created by Smita Hasole on 24-04-2018
 */

@Service("userDetailsService")
@Configurable
public class UserDetailServiceImpl implements UserDetailsService {
    UserRepo userRepo;
    @Autowired
    public UserDetailServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    private static List<UserObject> users = new ArrayList();


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData user = userRepo.findByEmailId(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
            return toUserDetails(new UserObject(user.getEmailId(),
                    user.getPassword(),
                    user.getRole()));
        }

        private UserDetails toUserDetails (UserObject userObject){
            return org.springframework.security.core.userdetails.User.withUsername(userObject.name).password(userObject.password).roles(userObject.role.name()).build();
        }

        private static class UserObject {
            private String name;
            private String password;
            private Role role;

            public UserObject(String name, String password, Role role) {
                this.name = name;
                this.password = password;
                this.role = role;
            }
        }
    }

