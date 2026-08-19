package com.taqaddum.app.ui.subjects;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.taqaddum.app.R;
import com.taqaddum.app.databinding.FragmentSubjectsBinding;
public class SubjectsFragment extends Fragment {
    private FragmentSubjectsBinding binding;
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater i,@Nullable ViewGroup c,@Nullable Bundle s){binding=FragmentSubjectsBinding.inflate(i,c,false);return binding.getRoot();}
    @Override public void onViewCreated(@NonNull View v,@Nullable Bundle s){SubjectAdapter adapter=new SubjectAdapter(subject->{Bundle b=new Bundle();b.putLong("subjectId",subject.id);NavHostFragment.findNavController(this).navigate(R.id.action_subjects_to_edit,b);});binding.subjectsRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));binding.subjectsRecycler.setAdapter(adapter);binding.addSubjectButton.setOnClickListener(x->NavHostFragment.findNavController(this).navigate(R.id.action_subjects_to_edit));new ViewModelProvider(this).get(SubjectsViewModel.class).subjects().observe(getViewLifecycleOwner(),items->{adapter.submitList(items);binding.emptyState.setVisibility(items==null||items.isEmpty()?View.VISIBLE:View.GONE);});}
    @Override public void onDestroyView(){super.onDestroyView();binding=null;}
}
