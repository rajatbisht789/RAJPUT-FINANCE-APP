package com.RajputFinance.Rajput.Finance.Controller;

import com.RajputFinance.Rajput.Finance.Model.Loan;
import com.RajputFinance.Rajput.Finance.Model.Repayment;
import com.RajputFinance.Rajput.Finance.Service.LoanService;
import com.RajputFinance.Rajput.Finance.Service.RepaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private RepaymentService repaymentService;

    @GetMapping
    public List<Loan> getLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping("/{loanNumber}/repayment")
    public List<Repayment> getRepaymentDetails(@PathVariable String loanNumber) {
        return repaymentService.getRepaymentDetailsByLoanNumber(loanNumber);
    }

    @GetMapping("/phone/{customerPhoneNumber}")
    public List<Loan> getLoansByPhoneNumber(@PathVariable String customerPhoneNumber) {
        return loanService.getAllLoansByPhoneNumber(customerPhoneNumber);
    }
    @GetMapping("/{loanNumber}")
    public Loan getLoanByLoanNumber(@PathVariable String loanNumber) {
        return loanService.getLoanDetailsWithRepaymentSchedule(loanNumber);
    }



    @PostMapping
    public Loan createLoan(@RequestBody Loan loan) {
        return loanService.createLoan(loan);
    }

    @PutMapping("/{loanNumber}")
    public Loan updateLoan(@PathVariable String loanNumber, @RequestBody Loan loan) {
        return loanService.updateLoan(loanNumber, loan);
    }

    @DeleteMapping("/{loanNumber}")
    public void deleteLoan(@PathVariable String loanNumber) {
        loanService.deleteLoan(loanNumber);
    }


}