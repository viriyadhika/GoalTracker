package com.example.goaltracker.util;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.goaltracker.data.GoalRoomDatabase;
import com.example.goaltracker.data.RecordDao;
import com.example.goaltracker.model.Record;


import java.util.List;

public class RecordRepository {

    private LiveData<List<Record>> allRecord;
    private RecordDao recordDao;

    public RecordRepository(Application application) {
        GoalRoomDatabase db = GoalRoomDatabase.getDatabase(application);
        recordDao = db.recordDao();
        allRecord = recordDao.getAllRecord();
    }

    public LiveData<List<Record>> getAllRecord() {return allRecord;}

    public void insert(Record record) {
        new RecordRepository.insertAsyncTask(recordDao).execute(record);
    }

    private static class insertAsyncTask extends AsyncTask<Record, Void, Void> {
        private RecordDao asyncTaskDao;

        public insertAsyncTask(RecordDao dao) {asyncTaskDao = dao;}

        @Override
        protected Void doInBackground(Record... records) {
            asyncTaskDao.insert(records[0]);
            return null;
        }
    }

    public void deleteAll() {new RecordRepository.deleteAllAsyncTask(recordDao).execute();}

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private RecordDao asyncTaskDao;

        public deleteAllAsyncTask(RecordDao dao) {asyncTaskDao = dao;}

        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.deleteAll();
            return null;
        }
    }

    public void delete(Record record) {new RecordRepository.deleteAsyncTask(recordDao).execute(record);}

    private static class deleteAsyncTask extends AsyncTask<Record, Void, Integer> {
        private RecordDao asyncTaskDao;

        public deleteAsyncTask(RecordDao dao) {asyncTaskDao = dao;}

        @Override
        protected Integer doInBackground(Record... records) {
            int updatedIndex;
            updatedIndex = asyncTaskDao.deleteRecord(records[0].getId());
            //Auto-boxing operation
            return updatedIndex;
        }
    }

    /**
     * Match the record in the database with the same id
     * Then using update the database with the new record
     * @param record with id to be updated
     */
    public void update(Record record) {new RecordRepository.updateAsyncTask(recordDao).execute(record);}

    private static class updateAsyncTask extends AsyncTask<Record, Void, Void> {

        private RecordDao asyncTaskDao;

        public updateAsyncTask(RecordDao dao) {asyncTaskDao = dao;}

        @Override
        protected Void doInBackground(Record... records) {
            asyncTaskDao.updateRecord(records[0].getId(), records[0].getGoalid(), records[0].getDate(), records[0].getValue());
            return null;
        }
    }



}
