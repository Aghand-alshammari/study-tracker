package com.taqaddum.app.ui.statistics;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.taqaddum.app.R;
import com.taqaddum.app.databinding.FragmentStatisticsBinding;
public class StatisticsFragment extends Fragment {
    private FragmentStatisticsBinding binding;
    public StatisticsFragment() { super(R.layout.fragment_statistics); }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle state) {
        binding = FragmentStatisticsBinding.bind(view);
        MonthlyStatAdapter adapter = new MonthlyStatAdapter();
        binding.monthsRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.monthsRecycler.setAdapter(adapter);
        new ViewModelProvider(this).get(StatisticsViewModel.class).monthlyStats().observe(getViewLifecycleOwner(), adapter::submitList);
    }
    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
