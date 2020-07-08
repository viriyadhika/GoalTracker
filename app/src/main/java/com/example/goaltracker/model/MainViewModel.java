package com.example.goaltracker.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.goaltracker.BuildConfig;
import com.example.goaltracker.MainActivity;
import com.example.goaltracker.util.Constants;
import com.example.goaltracker.util.DateTimeHandler;
import com.example.goaltracker.util.GoalRepository;
import com.example.goaltracker.util.RecordRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public static final String TAG = "MainViewModel";

    private GoalRepository goalRepository;
    private RecordRepository recordRepository;

    private LiveData<List<Goal>> allGoal;

    public MainViewModel(@NonNull Application application) {
        super(application);
        goalRepository = new GoalRepository(application);
        recordRepository = new RecordRepository(application);
        allGoal = goalRepository.getAllGoal();
    }

    public LiveData<List<Goal>> getAllGoal() {return allGoal;}

    public void insert(Goal goal) {goalRepository.insert(goal);}

    //TODO: make delete return int
    public void delete(Goal goal) {goalRepository.delete(goal);}

    public void deleteAll() {goalRepository.deleteAll();}

    //TODO: make update return int
    public void update(Goal goal) {goalRepository.update(goal);}

    public LiveData<List<Record>> getRecord(Goal goal, long startingDate, long endingDate) {return recordRepository.getRecord(goal.getId(), startingDate, endingDate);}


    public int getProgress(List<Record> records) {
        int result = 0;
        for (Record record : records) {
            result += record.getValue();
        }
        return result;
    }

    public LiveData<Double> getProgressBar (final Goal goal, final LifecycleOwner owner) {

        final double[] progress = new double[1];
        LiveData<List<Record>> liveData = null;

        if (goal.getFrequency().equals(Constants.DAILY)) {
            liveData = getRecord(goal,
                    DateTimeHandler.getCalendarLong(DateTimeHandler.NOW),
                    DateTimeHandler.getCalendarLong(DateTimeHandler.NOW));
        } else if (goal.getFrequency().equals(Constants.WEEKLY)) {
            liveData = getRecord(goal,
                    DateTimeHandler.getCalendarLong(DateTimeHandler.LAST_WEEK),
                    DateTimeHandler.getCalendarLong(DateTimeHandler.NOW));
        } else if (goal.getFrequency().equals(Constants.MONTHLY)) {
            liveData = getRecord(goal,
                    DateTimeHandler.getCalendarLong(DateTimeHandler.LAST_MONTH),
                    DateTimeHandler.getCalendarLong(DateTimeHandler.NOW));
        }

        final MutableLiveData<Double> res = new MutableLiveData<>();

        assert liveData != null : "cannot obtain life data to display progress bar";
        liveData.observe(owner, new Observer<List<Record>>() {
            @Override
            public void onChanged(List<Record> records) {
                if (goalIsInDatabase(goal, owner)) {
                    progress[0] = getProgress(records);
                    res.setValue(progress[0]);
                }
            }
        });
        return res;
    }

    public boolean goalIsInDatabase(final Goal goal, LifecycleOwner owner) {
        final boolean[] result = new boolean[1];
        allGoal.observe(owner, new Observer<List<Goal>>() {
            @Override
            public void onChanged(List<Goal> goals) {
                result[0] = goals.contains(goal);
            }
        });
        return result[0];
    }


}
