package com.RajputFinance.Rajput.Finance.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Repayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loanNumber;
    private LocalDate emiDueDate;
    private LocalDate emiPaidDate;
    private Double emiAmount;
    private String repaymentStatus;
    private String upiRefNo;

    @Transient
    private String customerName;
    @Transient
    private String customerPhoneNumber;
}
