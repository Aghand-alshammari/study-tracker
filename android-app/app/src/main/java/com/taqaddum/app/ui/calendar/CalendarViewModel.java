package com.taqaddum.app.ui.calendar;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import com.taqaddum.app.TaqaddumApplication;
import com.taqaddum.app.data.StudySession;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class CalendarViewModel extends AndroidViewModel {
    private final LiveData<CalendarUiState> state;
    public CalendarViewModel(@NonNull Application app) { super(app); TaqaddumApplication taqaddum = (TaqaddumApplication) app; state = Transformations.map(taqaddum.getDatabase().studySessionDao().observeAll(), this::aggregate); }
    public LiveData<CalendarUiState> state() { return state; }
    private CalendarUiState aggregate(List<StudySession> sessions) {
        ZoneId zone = ZoneId.systemDefault(); Map<LocalDate, Integer> minutes = new HashMap<>();
        if (sessions != null) for (StudySession session : sessions) { if (session.durationMinutes <= 0) continue; LocalDate day = Instant.ofEpochMilli(session.startedAt).atZone(zone).toLocalDate(); minutes.merge(day, session.durationMinutes, Integer::sum); }
        Set<LocalDate> active = new HashSet<>(minutes.keySet()); LocalDate first = active.stream().min(LocalDate::compareTo).orElse(null); LocalDate today = LocalDate.now(zone);
        int current = streakEndingAt(active, active.contains(today) ? today : today.minusDays(1)); int longest = longestStreak(active); LocalDate monthStart = today.withDayOfMonth(1); int attendance = 0;
        for (LocalDate day : active) if (!day.isBefore(monthStart) && !day.isAfter(today)) attendance++;
        int absence = 0; if (first != null) { LocalDate start = first.isAfter(monthStart) ? first : monthStart; LocalDate lastCompleted = today.minusDays(1); if (!start.isAfter(lastCompleted)) { absence = (int) ChronoUnit.DAYS.between(start, lastCompleted) + 1; for (LocalDate day : active) if (!day.isBefore(start) && !day.isAfter(lastCompleted)) absence--; } }
        return new CalendarUiState(minutes, first, current, longest, attendance, Math.max(0, absence));
    }
    private int streakEndingAt(Set<LocalDate> active, LocalDate end) { int count = 0; for (LocalDate day = end; active.contains(day); day = day.minusDays(1)) count++; return count; }
    private int longestStreak(Set<LocalDate> active) { int longest = 0; for (LocalDate day : active) { if (active.contains(day.minusDays(1))) continue; int count = 1; while (active.contains(day.plusDays(count))) count++; longest = Math.max(longest, count); } return longest; }
}
