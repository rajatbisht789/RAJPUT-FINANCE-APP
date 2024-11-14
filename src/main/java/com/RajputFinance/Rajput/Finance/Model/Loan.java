package com.RajputFinance.Rajput.Finance.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "loans")
@Data
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loanNumber;
    private String customerName;
    private String customerPhoneNumber;
    private String phoneBrand;
    private String phoneModel;
    private Double productPrice;
    private Integer tenure;
    private LocalDate emiStartDate;
    private LocalDate emiEndDate;
    private String loanStatus;
    private Double loanAmount;
    private Double emiAmount;
    private LocalDate emiDueDate;

    @Transient
    private List<Repayment> repaymentSchedule;
}

