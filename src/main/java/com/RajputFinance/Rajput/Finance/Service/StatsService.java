package com.RajputFinance.Rajput.Finance.Service;

import com.RajputFinance.Rajput.Finance.Repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class StatsService {

    @Autowired
    private LoanRepository loanRepository;

    public long getPendingLoans() {
        long count = loanRepository.countByLoanStatus("open");
        return count;
    }

    public long getClosedLoans() {
        long count = loanRepository.countByLoanStatus("closed");
        return count;
    }

    public double getTotalAmount() {
        double totalLoanAmount = loanRepository.getTotalLoanAmount().orElse(0.0);
        return totalLoanAmount;
    }

    public double getEmiPerDay() {
        double totalLoanAmount = loanRepository.getTotalLoanAmount().orElse(0.0);
        long daysSinceStart = getDaysSinceStart();
        return totalLoanAmount / daysSinceStart;
    }

    public double getEmiForToday() {
        LocalDate today = LocalDate.now();
        double emiForToday = loanRepository.getEmiAmountForToday(today).orElse(0.0);
        return emiForToday;
    }

    public double getTotalEmiForCurrentMonth() {
        YearMonth currentMonth = YearMonth.now();
        LocalDate startOfMonth = currentMonth.atDay(1);
        LocalDate endOfMonth = currentMonth.atEndOfMonth();

        return loanRepository.getTotalEmiForMonth(startOfMonth, endOfMonth).orElse(0.0);
    }

    private long getDaysSinceStart() {
        // Assuming loans started on a certain date; replace with actual logic if needed
        Date startDate = new Date(2024 - 1900, 0, 1);  // e.g., January 1, 2024
        long diffInMillis = Math.abs(new Date().getTime() - startDate.getTime());
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }
}
