package com.taqaddum.app.ui.subjects;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.taqaddum.app.TaqaddumApplication;
import com.taqaddum.app.data.Subject;
import com.taqaddum.app.data.SubjectRepository;
import java.util.List;
public class SubjectsViewModel extends AndroidViewModel {
    private final SubjectRepository repository;
    public SubjectsViewModel(@NonNull Application app) { super(app); repository = ((TaqaddumApplication) app).getSubjectRepository(); }
    public LiveData<List<Subject>> subjects() { return repository.observeActiveSubjects(); }
    public LiveData<Subject> subject(long id) { return repository.observeById(id); }
    public void save(Subject subject) { repository.save(subject); }
    public void delete(Subject subject) { repository.delete(subject); }
}
