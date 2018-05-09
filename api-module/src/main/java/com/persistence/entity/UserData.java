package com.persistence.entity;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * created by Smita Hasole on 19-04-2018
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
public class UserData  {

    @Id
    @GeneratedValue
    long userId;
    @Column(unique = true)
    String emailId;
    String fName;
    String lName;
    @Column(unique = true)
    String empId;
    Role role;
    String password;
    @Column(columnDefinition = "int default 1")
    int enabled;
    @OneToMany
    List<Reimbursement> reimbursement;
    String status;

    public UserData(String emailId, String password, Role roles) {
        this.emailId = emailId;
        this.password = password;
        this.role = role;
    }

    //list of approver
}
