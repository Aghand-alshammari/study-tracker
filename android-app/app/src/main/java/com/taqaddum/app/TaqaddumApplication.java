package com.taqaddum.app;

import android.app.Application;
import com.taqaddum.app.data.SubjectRepository;
import com.taqaddum.app.data.TaqaddumDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaqaddumApplication extends Application {
    private final ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();
    private SubjectRepository subjectRepository;
    @Override public void onCreate() { super.onCreate(); subjectRepository = new SubjectRepository(TaqaddumDatabase.getInstance(this).subjectDao(), databaseExecutor); }
    public SubjectRepository getSubjectRepository() { return subjectRepository; }
}
