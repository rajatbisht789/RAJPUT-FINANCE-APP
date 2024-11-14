package com.RajputFinance.Rajput.Finance.Controller;

import com.RajputFinance.Rajput.Finance.Service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping("/open-loans")
    public long getPendingLoans() {
        return statsService.getPendingLoans();
    }

    @GetMapping("/closed-loans")
    public long getClosedLoans() {
        return statsService.getClosedLoans();
    }

    @GetMapping("/total-amount")
    public double getTotalAmount() {
        return statsService.getTotalAmount();
    }

    @GetMapping("/emi-per-day")
    public double getEmiPerDay() {
        return statsService.getEmiPerDay();
    }

    @GetMapping("/emi-for-today")
    public double getEmiForToday() {
        return statsService.getEmiForToday();
    }

    @GetMapping("/emi-total-month")
    public double getTotalEmiForCurrentMonth() {
        return statsService.getTotalEmiForCurrentMonth();
    }
}
