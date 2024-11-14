package com.RajputFinance.Rajput.Finance.Controller;

import com.RajputFinance.Rajput.Finance.Model.Repayment;
import com.RajputFinance.Rajput.Finance.Service.RepaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/repayments")
public class RepaymentController {
    @Autowired
    private RepaymentService repaymentService;

    @GetMapping
    public List<Repayment> getUnpaidRepayments(@RequestParam String loanNumber) {
        return repaymentService.getUnpaidRepayments(loanNumber);
    }

    @PostMapping("/submit-reference")
    public Repayment submitUpiReference(
            @RequestParam String loanNumber,
            @RequestParam LocalDate emiDueDate,
            @RequestParam String upiRefNo) {
        return repaymentService.submitUpiReference(loanNumber, emiDueDate, upiRefNo);
    }


    @GetMapping("getAllPaymentsExceptNotPaid")
    public List<Repayment> getAllRePaymentsExceptNotPaid() {
        return repaymentService.getAllRePaymentExceptNotPaid();
    }

    @GetMapping("/search")
    public List<Repayment> searchRePayments(
            @RequestParam(value = "loanNumber", defaultValue = "") String loanNumber,
            @RequestParam(value = "emiDueDate", defaultValue = "") LocalDate emiDueDate,
            @RequestParam(value = "emiPaidDate", defaultValue = "") LocalDate emiPaidDate,
            @RequestParam(value = "upiRefNo", defaultValue = "") String upiRefNo) {
        return repaymentService.searchRePayments(loanNumber, emiDueDate, emiPaidDate, upiRefNo);
    }

    @PutMapping("/{repaymentId}/repaymentStatus")
    public Repayment updateRePaymentStatus(@PathVariable Long repaymentId, @RequestParam("repaymentStatus") String repaymentStatus) {
        return repaymentService.updateRePaymentStatus(repaymentId, repaymentStatus);
    }
}

