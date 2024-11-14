package com.RajputFinance.Rajput.Finance.Service;

import com.RajputFinance.Rajput.Finance.Model.Loan;
import com.RajputFinance.Rajput.Finance.Model.Repayment;
import com.RajputFinance.Rajput.Finance.Model.User;
import com.RajputFinance.Rajput.Finance.Repository.LoanRepository;
import com.RajputFinance.Rajput.Finance.Repository.RepaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private RepaymentRepository repaymentRepository;

    @Autowired
    private RepaymentService repaymentService;

    @Autowired
    private UserService userService;

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loan getLoanDetailsWithRepaymentSchedule(String loanNumber) {
        Loan loan = loanRepository.findByLoanNumber(loanNumber).orElse(null);
        List<Repayment> repaymentSchedule = repaymentRepository.findByLoanNumber(loan.getLoanNumber());
        loan.setRepaymentSchedule(repaymentSchedule);
        return loan;
    }

    public Loan createLoan(Loan loan) {
        Loan response = loanRepository.save(loan);
        createRepayment(loan, response);
        if(!userService.checkIfPhoneNumberAlreadyExisted(loan.getCustomerPhoneNumber())){
            User newUser = new User();
            newUser.setUserName(loan.getCustomerName());
            newUser.setPhoneNumber(loan.getCustomerPhoneNumber());
            newUser.setAdmin(false);
            userService.createNewUser(newUser);
        }
        return response;
    }

    public Loan updateLoan(String loanNumber, Loan loan) {
        Loan existingLoan = loanRepository.findByLoanNumber(loanNumber).orElse(null);
        if (existingLoan != null) {
            loan.setId(existingLoan.getId());
            Loan response = loanRepository.save(loan);
//            repaymentService.deleteAllRepaymentsByLoanNumber(loanNumber);
//            createRepayment(loan, response);
            return response;
        }
        return null;
    }

    private void createRepayment(Loan loan, Loan response) {
        if (response !=null){
            repaymentService.generateRepayments(
                    loan.getTenure(),
                    loan.getLoanNumber(),
                    loan.getEmiStartDate(),
                    loan.getEmiEndDate(),
                    loan.getEmiAmount(),
                    loan.getEmiDueDate()
            );
        }
    }

    public void deleteLoan(String loanNumber) {
        Loan loan = loanRepository.findByLoanNumber(loanNumber).orElse(null);
        if (loan != null) {
            loanRepository.delete(loan);
            repaymentService.deleteAllRepaymentsByLoanNumber(loanNumber);
        }
    }

    public List<Loan> getAllLoansByPhoneNumber(String customerPhoneNumber) {
        return loanRepository.findAllByCustomerPhoneNumber(customerPhoneNumber);
    }
}