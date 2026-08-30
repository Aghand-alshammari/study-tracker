package com.taqaddum.app.ui.statistics;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import com.taqaddum.app.TaqaddumApplication;
import com.taqaddum.app.data.StudySession;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class StatisticsViewModel extends AndroidViewModel {
    private final LiveData<List<MonthlyStat>> monthlyStats;
    public StatisticsViewModel(@NonNull Application app) {
        super(app);
        TaqaddumApplication taqaddum = (TaqaddumApplication) app;
        monthlyStats = Transformations.map(taqaddum.getDatabase().studySessionDao().observeAll(), this::aggregate);
    }
    public LiveData<List<MonthlyStat>> monthlyStats() { return monthlyStats; }
    private List<MonthlyStat> aggregate(List<StudySession> sessions) {
        Calendar now = Calendar.getInstance();
        Map<String, Integer> minutes = new LinkedHashMap<>();
        Map<String, Integer> days = new LinkedHashMap<>();
        Map<String, String> names = new LinkedHashMap<>();
        Locale arabic = new Locale("ar", "SA");
        String[] monthNames = new DateFormatSymbols(arabic).getMonths();
        for (int offset = 0; offset < 12; offset++) {
            Calendar month = (Calendar) now.clone(); month.add(Calendar.MONTH, -offset);
            String key = month.get(Calendar.YEAR) + "-" + month.get(Calendar.MONTH);
            minutes.put(key, 0);
            days.put(key, offset == 0 ? now.get(Calendar.DAY_OF_MONTH) : month.getActualMaximum(Calendar.DAY_OF_MONTH));
            names.put(key, monthNames[month.get(Calendar.MONTH)] + " " + month.get(Calendar.YEAR));
        }
        if (sessions != null) for (StudySession session : sessions) {
            Calendar date = Calendar.getInstance(); date.setTimeInMillis(session.startedAt);
            String key = date.get(Calendar.YEAR) + "-" + date.get(Calendar.MONTH);
            if (minutes.containsKey(key)) minutes.put(key, minutes.get(key) + session.durationMinutes);
        }
        List<MonthlyStat> result = new ArrayList<>();
        for (String key : minutes.keySet()) result.add(new MonthlyStat(names.get(key), minutes.get(key) / 60.0 / days.get(key)));
        return result;
    }
}
