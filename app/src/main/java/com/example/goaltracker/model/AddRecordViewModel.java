package com.example.goaltracker.model;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.goaltracker.exception.DateAfterTodayException;
import com.example.goaltracker.exception.DateFormatInvalidException;
import com.example.goaltracker.exception.DuplicateRecordException;
import com.example.goaltracker.util.Constants;
import com.example.goaltracker.util.DateTimeHandler;
import com.example.goaltracker.util.GoalRepository;
import com.example.goaltracker.util.RecordRepository;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddRecordViewModel extends AndroidViewModel {

    public static final String TAG = "AddRecordViewModel";

    private GoalRepository goalRepository;
    private RecordRepository recordRepository;

    private AddRecordViewModelListener listener;

    public AddRecordViewModel(@NonNull Application application) {
        super(application);
        goalRepository = new GoalRepository(application);
        recordRepository = new RecordRepository(application);
    }

    public LiveData<List<Goal>> getAllGoals() {return goalRepository.getAllGoal();}

    public void verifyDateFromCalendar(String datePicked) {

        try {
            DateTimeHandler.verifyDate(datePicked);
        } catch (DateAfterTodayException e) {
            listener.onDateAfterToday();
        } catch (DateFormatInvalidException e) {
            listener.onDateFormatInvalid();
        }

    }

    public void verifyDateFromEditText(String date) {
        try {
            DateTimeHandler.verifyDate(date);
            listener.onDateVerifiedFromEditText(date);
        } catch (DateAfterTodayException e) {
            listener.onDateAfterToday();
        } catch (DateFormatInvalidException e) {
            listener.onDateFormatInvalid();
        }
    }

    public interface AddRecordViewModelListener {
        void onDateFormatInvalid ();
        void onDateAfterToday ();
        void onDateVerifiedFromEditText(String date);
        void onSaveDuplicateFound();
        void onSaveSuccessful();
    }

    public void setAddRecordViewModelListener (AddRecordViewModelListener listener) {
        this.listener = listener;
    }

    private void checkDuplicate(final Record toCheck) throws DuplicateRecordException {
        if (recordRepository.getAllRecordMainThread() != null) {
            for (Record record : recordRepository.getAllRecordMainThread()) {
                if (record.getDate() == toCheck.getDate() && record.getGoalid() == toCheck.getGoalid()) {
                    throw new DuplicateRecordException();
                }
            }
        }
    }

    public void insert(Record toAdd) {
        recordRepository.insert(toAdd);
    }

    public void saveRecord(Map<Goal, Double> map, String dateString) {

        List<Record> records = new ArrayList<>();

        try {
            DateTimeHandler.verifyDate(dateString);
            long date = DateTimeHandler.stringToLongDate(dateString);

            for (Map.Entry<Goal, Double> entry : map.entrySet()) {
                Goal goal = entry.getKey();
                double value = entry.getValue();
                Record record = new Record(goal.getId(), date, value);
                this.checkDuplicate(record);
                records.add(record);
            }

            for (Record validRecord : records) {
                this.insert(validRecord);
            }

            listener.onSaveSuccessful();

        } catch (DateAfterTodayException e) {
            listener.onDateAfterToday();
        } catch (DateFormatInvalidException e) {
            listener.onDateFormatInvalid();
        } catch (DuplicateRecordException e) {
            listener.onSaveDuplicateFound();
        }
    }


}
