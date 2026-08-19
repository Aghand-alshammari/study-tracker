package com.taqaddum.app.ui.subjects;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.taqaddum.app.R;
import com.taqaddum.app.data.Subject;
import com.taqaddum.app.databinding.FragmentEditSubjectBinding;
public class EditSubjectFragment extends Fragment {
    private FragmentEditSubjectBinding binding; private SubjectsViewModel vm; private Subject editing;
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater i,@Nullable ViewGroup c,@Nullable Bundle s){binding=FragmentEditSubjectBinding.inflate(i,c,false);return binding.getRoot();}
    @Override public void onViewCreated(@NonNull View v,@Nullable Bundle s){vm=new ViewModelProvider(this).get(SubjectsViewModel.class);long id=getArguments()==null?-1:getArguments().getLong("subjectId",-1);if(id>0)vm.subject(id).observe(getViewLifecycleOwner(),subject->{if(subject!=null&&editing==null){editing=subject;binding.screenTitle.setText(R.string.edit_subject);binding.nameInput.setText(subject.name);binding.targetInput.setText(String.valueOf(subject.weeklyTargetMinutes/60.0));binding.deleteButton.setVisibility(View.VISIBLE);}});binding.saveButton.setOnClickListener(x->save());binding.deleteButton.setOnClickListener(x->{if(editing!=null){vm.delete(editing);Toast.makeText(requireContext(),R.string.subject_deleted,Toast.LENGTH_SHORT).show();NavHostFragment.findNavController(this).popBackStack();}});}
    private void save(){String name=binding.nameInput.getText()==null?"":binding.nameInput.getText().toString();String hours=binding.targetInput.getText()==null?"":binding.targetInput.getText().toString();binding.nameLayout.setError(null);binding.targetLayout.setError(null);if(!SubjectInputValidator.hasValidName(name)){binding.nameLayout.setError(getString(R.string.name_required));return;}Integer minutes=SubjectInputValidator.hoursToMinutes(hours);if(minutes==null){binding.targetLayout.setError(getString(R.string.target_invalid));return;}Subject subject=editing==null?new Subject(name.trim(),minutes):editing;subject.name=name.trim();subject.weeklyTargetMinutes=minutes;vm.save(subject);Toast.makeText(requireContext(),R.string.subject_saved,Toast.LENGTH_SHORT).show();NavHostFragment.findNavController(this).popBackStack();}
    @Override public void onDestroyView(){super.onDestroyView();binding=null;}
}
