package com.example.goaltracker.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.goaltracker.model.Record;
import com.example.goaltracker.util.Constants;

import java.util.List;

@Dao
public interface RecordDao {

    @Insert
    void insert(Record record);

    @Query("DELETE FROM " + Constants.REC_TABLE_NAME)
    void deleteAll();

    @Query("DELETE FROM " + Constants.REC_TABLE_NAME + " WHERE id = :id")
    int deleteRecord(int id);

    @Query("UPDATE " + Constants.REC_TABLE_NAME
            + " SET " + Constants.REC_GOAL_ID_COLUMN_NAME + " = :goalid,"
            + Constants.REC_DATE_COLUMN_NAME + "= :date, "
            + Constants.REC_VALUE_COLUMN_NAME + "= :value " +
            "WHERE id = :id")
    int updateRecord(int id, int goalid, long date, double value);

    @Query("SELECT * FROM " + Constants.REC_TABLE_NAME + " ORDER BY " + Constants.REC_DATE_COLUMN_NAME + " ASC")
    LiveData<List<Record>> getAllRecord();

    @Query("SELECT * FROM " + Constants.REC_TABLE_NAME
            + " WHERE " + Constants.REC_GOAL_ID_COLUMN_NAME + "= :goalid" + " AND "
            + Constants.REC_DATE_COLUMN_NAME + ">= :startDate" + " AND "
            + Constants.REC_DATE_COLUMN_NAME + "<= :endDate"
            + " ORDER BY " + Constants.REC_DATE_COLUMN_NAME + " ASC")
    LiveData<List<Record>> getRecord(int goalid, long startDate, long endDate);

    @Query("SELECT * FROM " + Constants.REC_TABLE_NAME
            + " WHERE " + Constants.REC_GOAL_ID_COLUMN_NAME + "= :goalid"
            + " ORDER BY " + Constants.REC_DATE_COLUMN_NAME + " DESC")
    LiveData<List<Record>> getRecord(int goalid);

}
