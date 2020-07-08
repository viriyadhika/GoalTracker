package com.example.goaltracker.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.goaltracker.util.GoalRepository;
import com.example.goaltracker.util.RecordRepository;

import java.util.List;

public class StatisticsViewModel extends AndroidViewModel {

    RecordRepository recordRepository;
    GoalRepository goalRepository;

    Goal currentGoal;

    public StatisticsViewModel(@NonNull Application application) {
        super(application);
        recordRepository = new RecordRepository(application);
        goalRepository = new GoalRepository(application);
    }

    public LiveData<List<Record>> getRecord (Goal goal, long startDate, long endDate) {
        return recordRepository.getRecord(goal.getId(), startDate, endDate);
    }




}
