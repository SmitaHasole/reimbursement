package com.service.serviceImpl;

import com.persistence.entity.Reimbursement;
import com.persistence.repo.ReimbursementRepo;
import com.service.ReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by Smita Hasole on 27-04-2018
 */
@Service
public class ReimbursementServiceImpl implements ReimbursementService {

    ReimbursementRepo reimbursementRepo;

    @Autowired
    public ReimbursementServiceImpl(ReimbursementRepo reimbursementRepo){
        this.reimbursementRepo = reimbursementRepo;
    }

    @Override
    public Reimbursement saveReimbursement(Reimbursement reimbursement) {
        return reimbursementRepo.save(reimbursement);
    }

    @Override
    public List<Reimbursement> getAllUnapprovedApplications() {
        return reimbursementRepo.findByStatus("Unapproved");
    }

    @Override
    public List<Reimbursement> getAll() {
        return reimbursementRepo.findAll();
    }

    @Override
    public List<Reimbursement> getAllApprovedApplications() {
        return reimbursementRepo.findByStatus("Approved");
    }

    @Override
    public List<Reimbursement> getAllCompletedApplications() {
        return reimbursementRepo.findByStatus("Completed");
    }

    @Override
    public Reimbursement updateStatus(String status, Long id) {
        return reimbursementRepo.updateStatus(id,status);
    }
}
