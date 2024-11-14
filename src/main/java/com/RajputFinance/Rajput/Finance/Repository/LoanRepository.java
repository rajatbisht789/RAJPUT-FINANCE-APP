package com.RajputFinance.Rajput.Finance.Repository;

import com.RajputFinance.Rajput.Finance.Model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByLoanNumber(String loanNumber);

    long countByLoanStatus(String status);

    @Query("SELECT SUM(l.loanAmount) FROM Loan l")
    Optional<Double> getTotalLoanAmount();

    @Query("SELECT SUM(l.emiAmount) FROM Loan l WHERE l.emiDueDate = :dueDate")
    Optional<Double> getEmiAmountForToday(@Param("dueDate") LocalDate dueDate);

    List<Loan> findAllByCustomerPhoneNumber(String customerPhoneNumber);

    @Query("SELECT SUM(l.emiAmount) FROM Loan l WHERE l.emiDueDate BETWEEN :startOfMonth AND :endOfMonth")
    Optional<Double> getTotalEmiForMonth(LocalDate startOfMonth, LocalDate endOfMonth);
}
