package com.service;

import com.controller.EmailExistsException;
import com.persistence.entity.Reimbursement;
import com.persistence.entity.UserData;


import java.util.List;

/**
 * created by Smita Hasole on 23-04-2018
 */

public interface UserService {
    public UserData registerNewUserAccount(UserData user) throws EmailExistsException;
    public UserData getUserById(long id);
    public List<UserData> getAllUsers();
    public UserData getUserByEmail(String email);
    public UserData getUserByReimbursement(Reimbursement reimbursement);
    public UserData updateUserData(UserData user);
}
