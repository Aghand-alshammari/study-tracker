package com.taqaddum.app.ui.statistics;

public class MonthlyStat {
    public final String monthName;
    public final double dailyAverageHours;
    public MonthlyStat(String monthName, double dailyAverageHours) {
        this.monthName = monthName;
        this.dailyAverageHours = dailyAverageHours;
    }
}
