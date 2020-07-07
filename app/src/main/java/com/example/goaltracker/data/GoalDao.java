package com.example.goaltracker.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.goaltracker.model.Goal;
import com.example.goaltracker.util.Constants;

import java.util.List;

/**
 * CRUD Operation for Goal class
 */
@Dao
public interface GoalDao {

    @Insert
    void insert(Goal goal);

    @Query("DELETE FROM " + Constants.GOAL_TABLE_NAME)
    void deleteAll();

    @Query("DELETE FROM " + Constants.GOAL_TABLE_NAME + " WHERE id = :id")
    int deleteAGoal(int id);

    @Query("UPDATE " + Constants.GOAL_TABLE_NAME
            + " SET " + Constants.GOAL_NAME_COLUMN_NAME + " = :goal,"
            + Constants.GOAL_TARGET_COLUMN_NAME + " = :moreThanValue,"
            + Constants.GOAL_VALUE_COLUMN_NAME + " = :value,"
            + Constants.GOAL_FREQUENCY_NAME + " = :frequency"
            + " WHERE id = :id")
    int updateGoal(int id, String goal, boolean moreThanValue, double value, String frequency);

    @Query("SELECT * FROM " + Constants.GOAL_TABLE_NAME + " ORDER BY " + Constants.GOAL_NAME_COLUMN_NAME + " DESC")
    LiveData<List<Goal>> getAllGoals();

}
