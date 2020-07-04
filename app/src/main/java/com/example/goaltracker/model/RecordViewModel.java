package com.example.goaltracker.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.goaltracker.util.RecordRepository;

import java.util.List;

public class RecordViewModel extends AndroidViewModel {

    public static final String TAG = "Record View Model";
    public RecordRepository recordRepository;
    public LiveData<List<Record>> allRecord;

    public RecordViewModel(@NonNull Application application) {
        super(application);
        recordRepository = new RecordRepository(application);
        allRecord = recordRepository.getAllRecord();
    }

    public LiveData<List<Record>> getAllRecord() {return allRecord;};

    public LiveData<List<Record>> getRecord (int id, long startDate, long endDate) {return recordRepository.getRecord(id, startDate, endDate);}

    public void insert(Record record) {
        recordRepository.insert(record);

    }



    //TODO: make delete return int
    public void delete(Record record) {recordRepository.delete(record);}

    public void deleteAll() {recordRepository.deleteAll();}

    //TODO: make update return int
    public void update(Record record) {recordRepository.update(record);}

}
