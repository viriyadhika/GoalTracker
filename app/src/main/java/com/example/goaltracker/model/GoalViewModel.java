package com.example.goaltracker.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.goaltracker.util.GoalRepository;

import java.util.List;

public class GoalViewModel extends AndroidViewModel {

    private GoalRepository goalRepository;
    private LiveData<List<Goal>> allGoal;

    public GoalViewModel(@NonNull Application application) {
        super(application);
        goalRepository = new GoalRepository(application);
        allGoal = goalRepository.getAllGoal();
    }

    public LiveData<List<Goal>> getAllGoal() {return allGoal;}

    public void insert(Goal goal) {goalRepository.insert(goal);}

    //TODO: make delete return int
    public void delete(Goal goal) {goalRepository.delete(goal);}

    public void deleteAll() {goalRepository.deleteAll();}

    //TODO: make update return int
    public void update(Goal goal) {goalRepository.update(goal);}

}
