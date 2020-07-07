package com.example.goaltracker.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.util.TableInfo;

import com.example.goaltracker.util.Constants;

@Entity(tableName = Constants.REC_TABLE_NAME, foreignKeys = @ForeignKey(entity = Goal.class,
        parentColumns = "id",
        childColumns = Constants.REC_GOAL_ID_COLUMN_NAME,
        onDelete = ForeignKey.CASCADE))
public class Record {

    @PrimaryKey(autoGenerate =  true)
    private int id;

    @ColumnInfo(name = Constants.REC_GOAL_ID_COLUMN_NAME)
    private int goalid;

    @ColumnInfo(name = Constants.REC_DATE_COLUMN_NAME)
    private long date;

    @ColumnInfo(name = Constants.REC_VALUE_COLUMN_NAME)
    private double value;

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

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", goalid=" + goalid +
                ", date=" + date +
                ", value=" + value +
                '}';
    }
}
