package com.service;

import com.persistence.entity.Reimbursement;

import java.util.List;

/**
 * created by Smita Hasole on 27-04-2018
 */

public interface ReimbursementService {
    public Reimbursement saveReimbursement(Reimbursement reimbursement);
    public List<Reimbursement> getAllUnapprovedApplications();
    public List<Reimbursement> getAll();
    public List<Reimbursement> getAllApprovedApplications();
    public List<Reimbursement> getAllCompletedApplications();
    public Reimbursement updateStatus(String status, Long id);
 }
