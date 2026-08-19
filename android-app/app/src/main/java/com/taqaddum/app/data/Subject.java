package com.taqaddum.app.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subjects")
public class Subject {
    @PrimaryKey(autoGenerate = true) public long id;
    public Long goalId;
    @NonNull public String name;
    public int weeklyTargetMinutes;
    public boolean archived;
    public long createdAt;

    public Subject(@NonNull String name, int weeklyTargetMinutes) {
        this.name = name; this.weeklyTargetMinutes = weeklyTargetMinutes;
        this.archived = false; this.createdAt = System.currentTimeMillis();
    }
}
