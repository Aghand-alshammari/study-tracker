package com.taqaddum.app.ui.statistics;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.taqaddum.app.R;
import com.taqaddum.app.databinding.ItemMonthlyStatBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MonthlyStatAdapter extends RecyclerView.Adapter<MonthlyStatAdapter.Holder> {
    private final List<MonthlyStat> items = new ArrayList<>();
    public void submitList(List<MonthlyStat> stats) { items.clear(); if (stats != null) items.addAll(stats); notifyDataSetChanged(); }
    @NonNull @Override public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { return new Holder(ItemMonthlyStatBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)); }
    @Override public void onBindViewHolder(@NonNull Holder holder, int position) { holder.bind(items.get(position)); }
    @Override public int getItemCount() { return items.size(); }
    static class Holder extends RecyclerView.ViewHolder {
        private final ItemMonthlyStatBinding binding;
        Holder(ItemMonthlyStatBinding binding) { super(binding.getRoot()); this.binding = binding; }
        void bind(MonthlyStat stat) {
            binding.monthName.setText(stat.monthName);
            binding.dailyAverage.setText(binding.getRoot().getContext().getString(R.string.daily_average_hours, String.format(new Locale("ar", "SA"), "%.1f", stat.dailyAverageHours)));
        }
    }
}
