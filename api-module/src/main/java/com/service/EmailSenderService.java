package com.service;

import com.persistence.entity.Reimbursement;

/**
 * created by Smita Hasole on 02-05-2018
 */

public interface EmailSenderService {
    public void sendMail(String msg, Reimbursement reimbursement);
}
