package com.RajputFinance.Rajput.Finance.Service;

import com.RajputFinance.Rajput.Finance.Model.Loan;
import com.RajputFinance.Rajput.Finance.Model.Payment;
import com.RajputFinance.Rajput.Finance.Model.Repayment;
import com.RajputFinance.Rajput.Finance.Repository.LoanRepository;
import com.RajputFinance.Rajput.Finance.Repository.RepaymentRepository;
import com.RajputFinance.Rajput.Finance.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RepaymentService {
    @Autowired
    private RepaymentRepository repaymentRepository;

    @Autowired
    private LoanRepository loanRepository;

    public List<Repayment> generateRepayments(Integer tenure, String loanNumber, LocalDate emiStartDate, LocalDate emiEndDate, Double emiAmount, LocalDate dueDate) {
        List<Repayment> repayments = new ArrayList<>();
        LocalDate nextDueDate = emiStartDate.withDayOfMonth(dueDate.getDayOfMonth());
        int count = 1;
        while (count++ <= tenure && !nextDueDate.isAfter(emiEndDate)) {
            nextDueDate = nextDueDate.plusMonths(1);
            Repayment repayment = new Repayment();
            repayment.setLoanNumber(loanNumber);
            repayment.setEmiDueDate(nextDueDate);
            repayment.setEmiAmount(emiAmount);
            repayment.setRepaymentStatus("Not Paid");
            repayments.add(repayment);
        }

        repaymentRepository.saveAll(repayments);
        return repayments;
    }

    @Transactional
    public void deleteAllRepaymentsByLoanNumber(String loanNumber) {
        repaymentRepository.deleteAllByLoanNumber(loanNumber);
    }

    public List<Repayment> getUnpaidRepayments(String loanNumber) {
        return repaymentRepository.findByLoanNumberAndRepaymentStatus(loanNumber, "Not Paid");
    }

    public Repayment submitUpiReference(String loanNumber, LocalDate emiDueDate, String upiRefNo) {

        Repayment repayment = repaymentRepository.findByLoanNumberAndEmiDueDate(loanNumber, emiDueDate)
                .orElse(null);


        if(repayment!=null){
            repayment.setUpiRefNo(upiRefNo);
            repayment.setRepaymentStatus("Pending");
            repayment.setEmiPaidDate(LocalDate.now());
            return repaymentRepository.save(repayment);
        }
        return repayment;
    }

    public List<Repayment> getAllRePaymentExceptNotPaid() {
        List<Repayment> repaymentsList = repaymentRepository.findByRepaymentStatusNot("Not Paid");
        repaymentsList.stream().forEach(item -> {
            Optional<Loan> loan = loanRepository.findByLoanNumber(item.getLoanNumber());
            loan.ifPresent(i->{
                item.setCustomerName(i.getCustomerName());
                item.setCustomerPhoneNumber(i.getCustomerPhoneNumber());
            });
        });
        return repaymentsList;
    }

    public List<Repayment> searchRePayments(String loanNumber, LocalDate emiDueDate, LocalDate emiPaidDate, String upiRefNo) {
        return repaymentRepository.findByLoanNumberContainingOrEmiDueDateOrEmiPaidDateOrUpiRefNoContaining(
                loanNumber, emiDueDate, emiPaidDate, upiRefNo);
    }

    public Repayment updateRePaymentStatus(Long repaymentId, String repaymentStatus) {
        Repayment repayment = repaymentRepository.findById(repaymentId).orElseThrow(()-> new RuntimeException("Re-Payment not found"));
        repayment.setRepaymentStatus(repaymentStatus);
        return repaymentRepository.save(repayment);
    }

    public List<Repayment> getRepaymentDetailsByLoanNumber(String loanNumber) {
        return repaymentRepository.findByLoanNumber(loanNumber);
    }
}
