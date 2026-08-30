package com.taqaddum.app.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import java.util.List;

@Dao
public interface StudySessionDao {
    @Query("SELECT * FROM study_sessions ORDER BY startedAt DESC")
    LiveData<List<StudySession>> observeAll();
}
