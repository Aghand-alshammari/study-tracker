package com.taqaddum.app.data;

import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class SubjectRepository {
    private final SubjectDao dao;
    private final ExecutorService executor;
    public SubjectRepository(SubjectDao dao, ExecutorService executor) { this.dao = dao; this.executor = executor; }
    public LiveData<List<Subject>> observeActiveSubjects() { return dao.observeActiveSubjects(); }
    public LiveData<Subject> observeById(long id) { return dao.observeById(id); }
    public void save(Subject subject) { executor.execute(() -> { if (subject.id == 0) dao.insert(subject); else dao.update(subject); }); }
    public void delete(Subject subject) { executor.execute(() -> dao.delete(subject)); }
}
