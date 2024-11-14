package com.RajputFinance.Rajput.Finance.Repository;

import com.RajputFinance.Rajput.Finance.Model.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RepaymentRepository extends JpaRepository<Repayment, Long> {
    List<Repayment> findByLoanNumber(String loanNumber);
    List<Repayment> findByLoanNumberAndRepaymentStatus(String loanNumber, String repaymentStatus);
    Optional<Repayment> findByLoanNumberAndEmiDueDate(String loanNumber, LocalDate emiDueDate);

    @Modifying
    @Query("DELETE FROM Repayment r WHERE r.loanNumber = :loanNumber")
    void deleteAllByLoanNumber(@Param("loanNumber") String loanNumber);

    List<Repayment> findByLoanNumberContainingOrEmiDueDateOrEmiPaidDateOrUpiRefNoContaining(
            String loanNumber,
            LocalDate emiDueDate,
            LocalDate emiPaidDate,
            String upiRefNo
    );
    List<Repayment> findByRepaymentStatusNot(String repaymentStatus);

}
