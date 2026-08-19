package com.taqaddum.app.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "goals")
public class Goal {
    @PrimaryKey(autoGenerate = true) public long id;
    @NonNull public String title;
    @NonNull public String type;
    public Long deadline;
    public int weeklyTargetMinutes;

    public Goal(@NonNull String title, @NonNull String type, Long deadline, int weeklyTargetMinutes) {
        this.title = title; this.type = type; this.deadline = deadline; this.weeklyTargetMinutes = weeklyTargetMinutes;
    }
}
