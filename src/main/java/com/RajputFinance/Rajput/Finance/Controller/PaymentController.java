package com.RajputFinance.Rajput.Finance.Controller;

import com.RajputFinance.Rajput.Finance.Model.Payment;
import com.RajputFinance.Rajput.Finance.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/search")
    public List<Payment> searchPayments(
            @RequestParam(value = "loanId", defaultValue = "") String loanId,
            @RequestParam(value = "emiDate", defaultValue = "") String emiDate,
            @RequestParam(value = "emiPaidDate", defaultValue = "") String emiPaidDate,
            @RequestParam(value = "upiRefNo", defaultValue = "") String upiRefNo) {
        return paymentService.searchPayments(loanId, emiDate, emiPaidDate, upiRefNo);
    }

    @PutMapping("/{paymentId}/status")
    public Payment updatePaymentStatus(@PathVariable Long paymentId, @RequestParam("status") String status) {
        return paymentService.updatePaymentStatus(paymentId, status);
    }

    @GetMapping("/testing")
    public String testing() {
        return "payment working";
    }
}
