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
    private String goalName;

    @ColumnInfo(name = Constants.GOAL_TARGET_COLUMN_NAME)
    private boolean moreThanValue;

    @ColumnInfo(name = Constants.GOAL_VALUE_COLUMN_NAME)
    private double value;

    @ColumnInfo(name = Constants.GOAL_FREQUENCY_NAME)
    private String frequency;

    public Goal(String goalName, boolean moreThanValue, double value, String frequency) {
        this.goalName = goalName;
        this.moreThanValue = moreThanValue;
        this.value = value;
        this.frequency = frequency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public boolean isMoreThanValue() {
        return moreThanValue;
    }

    public void setMoreThanValue(boolean moreThanValue) {
        this.moreThanValue = moreThanValue;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
