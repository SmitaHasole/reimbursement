package com.persistence.repo;

import com.persistence.entity.Reimbursement;
import com.persistence.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * created by Smita Hasole on 19-04-2018
 */

public interface UserRepo extends JpaRepository<UserData, Long> {

    UserData findByEmailId(String emailId);
    UserData findByUserId(Long id);
    UserData findByReimbursement(Reimbursement reimbursement);
}
