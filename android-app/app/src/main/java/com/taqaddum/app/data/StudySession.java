package com.taqaddum.app.data;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "study_sessions", foreignKeys = @ForeignKey(entity = Subject.class, parentColumns = "id", childColumns = "subjectId", onDelete = ForeignKey.CASCADE), indices = @Index("subjectId"))
public class StudySession {
    @PrimaryKey(autoGenerate = true) public long id;
    public long subjectId;
    public long startedAt;
    public int durationMinutes;
    @Nullable public String note;

    public StudySession(long subjectId, long startedAt, int durationMinutes, @Nullable String note) {
        this.subjectId = subjectId; this.startedAt = startedAt; this.durationMinutes = durationMinutes; this.note = note;
    }
}
