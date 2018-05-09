package com.controller;

import com.persistence.ResponseBean;
import com.persistence.entity.Reimbursement;
import com.service.EmailSenderService;
import com.service.ReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * created by Smita Hasole on 26-04-2018
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(value = "/reimbursement")
public class ReimbursementController {

    ReimbursementService reimbursementService;
    EmailSenderService emailSenderService;

    @Autowired
    ReimbursementController(ReimbursementService reimbursementService, EmailSenderService emailSenderService) {
        this.reimbursementService = reimbursementService;
        this.emailSenderService = emailSenderService;
    }

    @GetMapping("/all")
    ResponseEntity<ResponseBean> getAllReimbursement() {
        List<Reimbursement> reimbursementList = reimbursementService.getAll();
        ResponseBean responseBean = new ResponseBean();
        if (!reimbursementList.isEmpty() || reimbursementList != null) {
            responseBean.setData(reimbursementList);
            responseBean.setMessage("data fetched successfully");
            responseBean.setStatus(HttpStatus.FOUND.toString());
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        }
        responseBean.setMessage("data not found");
        responseBean.setStatus(HttpStatus.NOT_FOUND.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBean);
    }

    @GetMapping("/all/approved")
    @Secured({"Financer" })
    ResponseEntity<ResponseBean> getAllApprovedReimbursement() {
        List<Reimbursement> reimbursementList = reimbursementService.getAllApprovedApplications();
        ResponseBean responseBean = new ResponseBean();
        if (!reimbursementList.isEmpty() || reimbursementList != null) {
            responseBean.setData(reimbursementList);
            responseBean.setMessage("data fetched successfully");
            responseBean.setStatus(HttpStatus.FOUND.toString());
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        }
        responseBean.setMessage("data not found");
        responseBean.setStatus(HttpStatus.NOT_FOUND.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBean);
    }
    @GetMapping("/all/unapproved")
    @Secured({ "Approver", "Financer" })
    ResponseEntity<ResponseBean> getAllUnapprovedReimbursement() {
        List<Reimbursement> reimbursementList = reimbursementService.getAllUnapprovedApplications();
        ResponseBean responseBean = new ResponseBean();
        if (!reimbursementList.isEmpty() || reimbursementList != null) {
            responseBean.setData(reimbursementList);
            responseBean.setMessage("data fetched successfully");
            responseBean.setStatus(HttpStatus.FOUND.toString());
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        }
        responseBean.setMessage("data not found");
        responseBean.setStatus(HttpStatus.NOT_FOUND.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBean);
    }

    @GetMapping("/all/completed")
    @Secured({ "Financer" })
    ResponseEntity<ResponseBean> getAllCompletedReimbursement() {
        List<Reimbursement> reimbursementList = reimbursementService.getAllCompletedApplications();
        ResponseBean responseBean = new ResponseBean();
        if (!reimbursementList.isEmpty() || reimbursementList != null) {
            responseBean.setData(reimbursementList);
            responseBean.setMessage("data fetched successfully");
            responseBean.setStatus(HttpStatus.FOUND.toString());
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        }
        responseBean.setMessage("data not found");
        responseBean.setStatus(HttpStatus.NOT_FOUND.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBean);
    }

    @PostMapping("/save")
    ResponseEntity<ResponseBean> saveReimbursement(@RequestBody Reimbursement reimbursement) {
        Reimbursement savedReimbursement = reimbursementService.saveReimbursement(reimbursement);
        ResponseBean responseBean = new ResponseBean();
        if (savedReimbursement != null) {
            responseBean.setData(savedReimbursement);
            responseBean.setMessage("data saved successfully");
            responseBean.setStatus(HttpStatus.CREATED.toString());
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        }
        responseBean.setMessage("data not found");
        responseBean.setStatus(HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBean);
    }

    @PostMapping("/approve")
    @Secured({ "Approver", "Financer" })
    ResponseEntity<ResponseBean> updateStatusForApprover(@RequestBody Reimbursement reimbursement) {
        int i = reimbursementService.updateStatus(reimbursement.getStatus(),reimbursement.getReimbursementId());
        ResponseBean responseBean = new ResponseBean();
        if (i != 0) {
            Reimbursement savedReimbursement = reimbursementService.getReimbursementById(reimbursement.getReimbursementId());
            responseBean.setData(reimbursement);
            if(savedReimbursement!=null && savedReimbursement.getReimbursementCategory().equalsIgnoreCase("Approved")){
                emailSenderService.sendMail("your form is approved",savedReimbursement);
            }
            responseBean.setMessage("data saved successfully");
            responseBean.setStatus(HttpStatus.CREATED.toString());
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        }
        responseBean.setMessage("data not found");
        responseBean.setStatus(HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBean);
    }

    @PostMapping("/complete")
    @Secured({ "Financer" })
    ResponseEntity<ResponseBean> updateStatusForFinancer(@RequestBody Reimbursement reimbursement) {
        int i = reimbursementService.updateStatus(reimbursement.getStatus(),reimbursement.getReimbursementId());
        ResponseBean responseBean = new ResponseBean();
        if (i != 0) {
            Reimbursement savedReimbursement = reimbursementService.getReimbursementById(reimbursement.getReimbursementId());
            responseBean.setData(reimbursement);
            if(savedReimbursement!=null && savedReimbursement.getReimbursementCategory().equalsIgnoreCase("Completed")){
                emailSenderService.sendMail("your form is approved",savedReimbursement);
            }
            responseBean.setMessage("data saved successfully");
            responseBean.setStatus(HttpStatus.CREATED.toString());
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        }
        responseBean.setMessage("data not found");
        responseBean.setStatus(HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBean);
    }
}


