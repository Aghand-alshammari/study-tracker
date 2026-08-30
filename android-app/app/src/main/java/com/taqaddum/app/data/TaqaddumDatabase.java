package com.taqaddum.app.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Goal.class, Subject.class, StudySession.class}, version = 2, exportSchema = false)
public abstract class TaqaddumDatabase extends RoomDatabase {
    private static volatile TaqaddumDatabase instance;
    public abstract SubjectDao subjectDao();
    public abstract StudySessionDao studySessionDao();

    public static TaqaddumDatabase getInstance(Context context) {
        if (instance == null) synchronized (TaqaddumDatabase.class) {
            if (instance == null) instance = Room.databaseBuilder(context.getApplicationContext(), TaqaddumDatabase.class, "taqaddum.db").addMigrations(MIGRATION_1_2).build();
        }
        return instance;
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override public void migrate(SupportSQLiteDatabase db) {
            db.execSQL("ALTER TABLE subjects ADD COLUMN colorHex TEXT NOT NULL DEFAULT '#FFF1A8'");
            db.execSQL("ALTER TABLE subjects ADD COLUMN hasTheory INTEGER NOT NULL DEFAULT 1");
            db.execSQL("ALTER TABLE subjects ADD COLUMN hasPractical INTEGER NOT NULL DEFAULT 1");
            db.execSQL("ALTER TABLE subjects ADD COLUMN lastStudiedAt INTEGER NOT NULL DEFAULT 0");
        }
    };
}
