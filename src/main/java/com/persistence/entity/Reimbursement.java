package com.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * created by Smita Hasole on 19-04-2018
 */
@Entity
@Getter
@Setter
public class Reimbursement {
    @Id
    @GeneratedValue
    Long reimbursementId;
    String reimbursementCategory;
    @OneToOne
    ReimbursementData reimbursementData;
    String status;
}
