package com.taqaddum.app.ui.calendar;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
public final class CalendarUiState {
    public final Map<LocalDate, Integer> minutesByDay; public final LocalDate firstStudyDay;
    public final int currentStreak, longestStreak, attendanceDays, absenceDays;
    public CalendarUiState(Map<LocalDate, Integer> minutesByDay, LocalDate firstStudyDay, int currentStreak, int longestStreak, int attendanceDays, int absenceDays) {
        this.minutesByDay = Collections.unmodifiableMap(minutesByDay); this.firstStudyDay = firstStudyDay; this.currentStreak = currentStreak; this.longestStreak = longestStreak; this.attendanceDays = attendanceDays; this.absenceDays = absenceDays;
    }
}
