package com.taqaddum.app.ui.calendar;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.taqaddum.app.R;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
public class CalendarFragment extends Fragment {
    private static final String SHOWN_MONTH = "shown_month";
    private LocalDate shownMonth = LocalDate.now().withDayOfMonth(1);
    private CalendarUiState calendarState;
    public CalendarFragment() { super(R.layout.fragment_calendar); }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle state) {
        super.onViewCreated(view, state);
        if (state != null && state.containsKey(SHOWN_MONTH)) shownMonth = LocalDate.parse(state.getString(SHOWN_MONTH));
        view.findViewById(R.id.previous_month).setOnClickListener(v -> moveMonth(view, -1));
        view.findViewById(R.id.next_month).setOnClickListener(v -> moveMonth(view, 1));
        new ViewModelProvider(this).get(CalendarViewModel.class).state().observe(getViewLifecycleOwner(), value -> { calendarState = value; render(view, value); });
    }
    @Override public void onSaveInstanceState(@NonNull Bundle outState) { outState.putString(SHOWN_MONTH, shownMonth.toString()); super.onSaveInstanceState(outState); }
    private void moveMonth(View root, int offset) { if (calendarState == null) return; shownMonth = shownMonth.plusMonths(offset); render(root, calendarState); }
    private void render(View root, CalendarUiState state) {
        Locale arabic = new Locale("ar", "SA");
        ((TextView) root.findViewById(R.id.month_title)).setText(shownMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy", arabic)));
        LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);
        updateArrow(root.findViewById(R.id.previous_month), true);
        updateArrow(root.findViewById(R.id.next_month), shownMonth.isBefore(currentMonth));
        GridLayout grid = root.findViewById(R.id.activity_grid); grid.removeAllViews(); grid.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        String[] weekdays = {"السبت", "الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة"}; for (String weekday : weekdays) grid.addView(label(weekday));
        int offset = (shownMonth.getDayOfWeek().getValue() + 1) % 7; for (int i = 0; i < offset; i++) grid.addView(label(""));
        LocalDate today = LocalDate.now();
        for (int number = 1; number <= shownMonth.lengthOfMonth(); number++) { LocalDate day = shownMonth.withDayOfMonth(number); int minutes = state.minutesByDay.getOrDefault(day, 0); TextView cell = label(String.valueOf(number)); cell.setMinHeight(dp(44)); cell.setTextColor(color(R.color.text_primary)); cell.setBackground(dayBackground(day, today, state.firstStudyDay, minutes)); cell.setContentDescription(dayDescription(day, today, state.firstStudyDay, minutes)); cell.setOnClickListener(v -> Toast.makeText(requireContext(), v.getContentDescription(), Toast.LENGTH_SHORT).show()); grid.addView(cell); }
    }
    private void updateArrow(View arrow, boolean enabled) { arrow.setEnabled(enabled); arrow.setAlpha(enabled ? 1f : 0.3f); }
    private TextView label(String text) { TextView view = (TextView) LayoutInflater.from(requireContext()).inflate(android.R.layout.simple_list_item_1, null, false); view.setText(text); view.setGravity(Gravity.CENTER); view.setTextSize(12); view.setTypeface(Typeface.DEFAULT, Typeface.BOLD); view.setPadding(0, 0, 0, 0); GridLayout.LayoutParams params = new GridLayout.LayoutParams(); params.width = 0; params.height = ViewGroup.LayoutParams.WRAP_CONTENT; params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); params.setMargins(dp(3), dp(3), dp(3), dp(3)); view.setLayoutParams(params); return view; }
    private GradientDrawable dayBackground(LocalDate day, LocalDate today, LocalDate first, int minutes) { int fill; if (day.isAfter(today) || (day.equals(today) && minutes == 0) || first == null || day.isBefore(first)) fill = color(R.color.activity_future); else if (minutes == 0) fill = color(R.color.activity_absent); else if (minutes < 30) fill = color(R.color.activity_1); else if (minutes < 60) fill = color(R.color.activity_2); else if (minutes < 120) fill = color(R.color.activity_3); else fill = color(R.color.activity_4); GradientDrawable drawable = new GradientDrawable(); drawable.setColor(fill); drawable.setCornerRadius(dp(8)); if (day.equals(today)) drawable.setStroke(dp(3), color(R.color.today_outline)); return drawable; }
    private String dayDescription(LocalDate day, LocalDate today, LocalDate first, int minutes) { String status; if (day.equals(today) && minutes == 0) status = getString(R.string.today_not_studied); else if (day.isAfter(today) || first == null || day.isBefore(first)) status = getString(R.string.future_day); else if (minutes == 0) status = getString(R.string.absent_day); else status = getString(R.string.study_minutes, minutes); return day.format(DateTimeFormatter.ofPattern("d MMMM", new Locale("ar", "SA"))) + "، " + status; }
    @ColorInt private int color(int id) { return ContextCompat.getColor(requireContext(), id); } private int dp(int value) { return Math.round(value * getResources().getDisplayMetrics().density); }
}
