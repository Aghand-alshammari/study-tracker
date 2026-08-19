package com.taqaddum.app.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface SubjectDao {
    @Query("SELECT * FROM subjects WHERE archived = 0 ORDER BY createdAt DESC") LiveData<List<Subject>> observeActiveSubjects();
    @Query("SELECT * FROM subjects WHERE id = :id LIMIT 1") LiveData<Subject> observeById(long id);
    @Insert long insert(Subject subject);
    @Update void update(Subject subject);
    @Delete void delete(Subject subject);
}
