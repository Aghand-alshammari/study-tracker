package com.taqaddum.app.ui.subjects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.taqaddum.app.R;
import com.taqaddum.app.data.Subject;
import com.taqaddum.app.databinding.FragmentSubjectsBinding;

public class SubjectsFragment extends Fragment {
    private FragmentSubjectsBinding binding;

    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle state) {
        binding = FragmentSubjectsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle state) {
        SubjectsViewModel model = new ViewModelProvider(this).get(SubjectsViewModel.class);
        SubjectAdapter adapter = new SubjectAdapter(subject -> {
            Toast.makeText(requireContext(), lastStudied(subject.lastStudiedAt), Toast.LENGTH_LONG).show();
            Bundle arguments = new Bundle();
            arguments.putLong("subjectId", subject.id);
            NavHostFragment.findNavController(this).navigate(R.id.action_subjects_to_edit, arguments);
        });
        binding.subjectsRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.subjectsRecycler.setAdapter(adapter);
        attachSwipeToDelete(adapter, model);
        binding.settingsButton.setOnClickListener(this::showSettingsMenu);
        updateThemeButton();
        binding.themeButton.setOnClickListener(v -> toggleTheme());
        model.subjects().observe(getViewLifecycleOwner(), items -> {
            adapter.submitList(items);
            binding.emptyState.setVisibility(items == null || items.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }

    private void attachSwipeToDelete(SubjectAdapter adapter, SubjectsViewModel model) {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override public boolean onMove(@NonNull RecyclerView recycler, @NonNull RecyclerView.ViewHolder source, @NonNull RecyclerView.ViewHolder target) { return false; }
            @Override public void onSwiped(@NonNull RecyclerView.ViewHolder holder, int direction) {
                Subject subject = adapter.subjectAt(holder.getBindingAdapterPosition());
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle(R.string.delete_subject_title)
                        .setMessage(R.string.delete_subject_message)
                        .setNegativeButton(R.string.cancel, (dialog, which) -> adapter.restore(subject))
                        .setPositiveButton(R.string.confirm_delete, (dialog, which) -> {
                            model.delete(subject);
                            Toast.makeText(requireContext(), R.string.subject_deleted, Toast.LENGTH_SHORT).show();
                        })
                        .setOnCancelListener(dialog -> adapter.restore(subject))
                        .show();
            }
        });
        helper.attachToRecyclerView(binding.subjectsRecycler);
    }

    private boolean isNightMode() { return (getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK) == android.content.res.Configuration.UI_MODE_NIGHT_YES; }
    private void updateThemeButton() { boolean night = isNightMode(); binding.themeButton.setText(night ? "☀" : "☾"); binding.themeButton.setContentDescription(getString(night ? R.string.switch_to_light_mode : R.string.switch_to_dark_mode)); }
    private void toggleTheme() { AppCompatDelegate.setDefaultNightMode(isNightMode() ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES); }
    private String lastStudied(long time) { if (time <= 0) return getString(R.string.last_studied_never); long days = Math.max(0, (System.currentTimeMillis() - time) / 86400000L); return days == 0 ? getString(R.string.last_studied_today) : getString(R.string.last_studied_days, days); }
    private void showSettingsMenu(View anchor) { PopupMenu menu = new PopupMenu(requireContext(), anchor); menu.getMenu().add(getString(R.string.settings)); menu.getMenu().add(getString(R.string.support)); menu.setOnMenuItemClickListener(item -> { Toast.makeText(requireContext(), item.getTitle(), Toast.LENGTH_SHORT).show(); return true; }); menu.show(); }
    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
