package com.example.goaltracker.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.goaltracker.util.Constants;

@Entity(tableName = Constants.REC_TABLE_NAME)
public class Record {

    @PrimaryKey(autoGenerate =  true)
    private int id;

    @ColumnInfo(name = Constants.REC_GOAL_ID_COLUMN_NAME)
    private int goalid;

    @ColumnInfo(name = Constants.REC_DATE_COLUMN_NAME)
    private long date;

    @ColumnInfo(name = Constants.REC_VALUE_COLUMN_NAME)
    private double value;

    //TODO: To implement Record Class constructor
    public Record(int goalid, long date, double value) {
        this.goalid = goalid;
        this.date = date;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoalid() {
        return goalid;
    }

    public void setGoalid(int goalid) {
        this.goalid = goalid;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
