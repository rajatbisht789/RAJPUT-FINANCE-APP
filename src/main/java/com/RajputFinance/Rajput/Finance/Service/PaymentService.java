package com.RajputFinance.Rajput.Finance.Service;

import com.RajputFinance.Rajput.Finance.Model.Payment;
import com.RajputFinance.Rajput.Finance.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> searchPayments(String loanId, String emiDate, String emiPaidDate, String upiRefNo) {
        return paymentRepository.findByLoanIdContainingOrEmiDateContainingOrEmiPaidDateContainingOrUpiRefNoContaining(
                loanId, emiDate, emiPaidDate, upiRefNo);
    }

    public Payment updatePaymentStatus(Long paymentId, String status) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }
}

