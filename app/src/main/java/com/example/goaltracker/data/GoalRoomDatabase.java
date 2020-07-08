package com.example.goaltracker.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.goaltracker.model.Goal;
import com.example.goaltracker.model.Record;
import com.example.goaltracker.util.Constants;

@Database(entities = {Goal.class, Record.class}, version = 8)
public abstract class GoalRoomDatabase extends RoomDatabase {
    public abstract GoalDao goalDao();
    public abstract RecordDao recordDao();

    private static volatile GoalRoomDatabase INSTANCE;

    public static GoalRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GoalRoomDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        GoalRoomDatabase.class, Constants.DATABASE_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }

        return INSTANCE;
    }

}
