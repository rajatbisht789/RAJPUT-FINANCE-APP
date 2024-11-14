package com.RajputFinance.Rajput.Finance.Repository;

import com.RajputFinance.Rajput.Finance.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByLoanIdContainingOrEmiDateContainingOrEmiPaidDateContainingOrUpiRefNoContaining(
            String loanId, String emiDate, String emiPaidDate, String upiRefNo);
}
