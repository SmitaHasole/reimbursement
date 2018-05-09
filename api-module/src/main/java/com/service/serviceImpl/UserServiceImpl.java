package com.service.serviceImpl;

import com.controller.EmailExistsException;
import com.persistence.entity.Reimbursement;
import com.persistence.entity.Role;
import com.persistence.entity.UserData;
import com.persistence.repo.UserRepo;
import com.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by Smita Hasole on 23-04-2018
 */
@Service
@Configurable
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepo userRepo) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;    }

    @Override
    public UserData registerNewUserAccount(UserData accountDto) throws EmailExistsException {
        if (emailExist(accountDto.getEmailId())) {
            throw new EmailExistsException(
                    "There is an account with that email adress:" + accountDto.getEmailId());
        }
        UserData user = new UserData();
        user.setFName(accountDto.getFName());
        user.setLName(accountDto.getLName());
        System.out.println(accountDto.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(accountDto.getPassword()));

        user.setEmailId(accountDto.getEmailId());
        user.setRole(Role.Admin);
        return userRepo.save(user);
    }

    public boolean emailExist(String emailId) {
        if (userRepo.findByEmailId(emailId) != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserData getUserById(long id) {
        return userRepo.findByUserId(id);
    }

    @Override
    public UserData getUserByEmail(String email) {
        return userRepo.findByEmailId(email);
    }

    @Override
    public UserData getUserByReimbursement(Reimbursement reimbursement) {
        return userRepo.findByReimbursement(reimbursement);
    }

    @Override
    public UserData updateUserData(UserData user) {
        return userRepo.save(user);
    }

    @Override
    public List<UserData> getAllUsers() {
        return userRepo.findAll();
    }
}
