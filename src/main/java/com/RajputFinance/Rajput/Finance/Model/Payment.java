package com.RajputFinance.Rajput.Finance.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loanId;
    private Double amount;
    private String emiDate;
    private String emiPaidDate;
    private String upiRefNo;
    private String status;

}
