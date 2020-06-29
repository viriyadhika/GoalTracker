package com.example.goaltracker.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.goaltracker.util.Constants;

@Entity(tableName = Constants.GOAL_TABLE_NAME)
public class Goal {

    @PrimaryKey(autoGenerate =  true)
    private int id;

    @ColumnInfo(name = Constants.GOAL_NAME_COLUMN_NAME)
    private String goal;

    public Goal(String goal) {
        this.goal = goal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

}
