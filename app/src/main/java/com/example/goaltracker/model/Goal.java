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

    @ColumnInfo(name = Constants.GOAL_DEFAULT_COLUMN_NAME)
    private double defaultvalue;

    public Goal(String goalName, boolean moreThanValue, double value, String frequency, double defaultvalue) {
        this.goalName = goalName;
        this.moreThanValue = moreThanValue;
        this.value = value;
        this.frequency = frequency;
        this.defaultvalue = defaultvalue;
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

    public double getDefaultvalue() {
        return defaultvalue;
    }

    public void setDefaultvalue(double defaultvalue) {
        this.defaultvalue = defaultvalue;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "id=" + id +
                ", goalName='" + goalName + '\'' +
                ", moreThanValue=" + moreThanValue +
                ", value=" + value +
                ", frequency='" + frequency + '\'' +
                ", defaultvalue=" + defaultvalue +
                '}';
    }
}
