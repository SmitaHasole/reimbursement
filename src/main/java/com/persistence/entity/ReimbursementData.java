package com.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * created by Smita Hasole on 23-04-2018
 */

@Entity
@Getter
@Setter
public class ReimbursementData {
    @Id
    @GeneratedValue
    long id;
    String hotelName;
    int numberOfMembers;
    int billAmount;
    String hospitalName;
    String operationName;
    String mediClaimId;
    String flightName;
    String purpose;
    String place;
    String numberOfDays;

}
