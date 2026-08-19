package com.taqaddum.app.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Goal.class, Subject.class, StudySession.class}, version = 1, exportSchema = false)
public abstract class TaqaddumDatabase extends RoomDatabase {
    private static volatile TaqaddumDatabase instance;
    public abstract SubjectDao subjectDao();

    public static TaqaddumDatabase getInstance(Context context) {
        if (instance == null) synchronized (TaqaddumDatabase.class) {
            if (instance == null) instance = Room.databaseBuilder(context.getApplicationContext(), TaqaddumDatabase.class, "taqaddum.db").build();
        }
        return instance;
    }
}
