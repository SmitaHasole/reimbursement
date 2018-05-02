package com.persistence.repo;

import com.persistence.entity.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;


/**
 * created by Smita Hasole on 19-04-2018
 */

public interface ReimbursementRepo extends JpaRepository<Reimbursement, Long> {

    Reimbursement findByReimbursementId(String emailId);

    //    @Query("select e from Reimbursement e where e.status=Approved")
    List<Reimbursement> findByStatus(String status);

    List<Reimbursement> findAll();


    @Query("update Reimbursement u set u.status =status where u.reimbursementId =?1")
    @Modifying
    @Transactional
    Reimbursement updateStatus(Long id,String status);
//    @Query("update NewsFeed e set e.category=category where e.id=?1")
//    @Modifying
//    @Transactional
//    public void updatenewsCategory(Long id, String category);
}
