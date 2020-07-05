package com.example.goaltracker.util;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.goaltracker.data.GoalDao;
import com.example.goaltracker.data.GoalRoomDatabase;
import com.example.goaltracker.model.Goal;

import java.util.List;

/**
 * Responsible to interact with the GoalRoomDatabase class
 * Getting the data to pass to View Model in a form of live data
 * All the CRUD operations are done via Async Task
 */
public class GoalRepository {
    private LiveData<List<Goal>> allGoal;
    private GoalDao goalDao;

    public GoalRepository(Application application) {
        GoalRoomDatabase db = GoalRoomDatabase.getDatabase(application);
        goalDao = db.goalDao();
        allGoal = goalDao.getAllGoals();
    }

    public LiveData<List<Goal>> getAllGoal() {return allGoal;}

    public void insert(Goal goal) {
        new insertAsyncTask(goalDao).execute(goal);
    }

    private static class insertAsyncTask extends AsyncTask<Goal, Void, Void> {
        private GoalDao asyncTaskDao;

        public insertAsyncTask(GoalDao dao) {asyncTaskDao = dao;}

        @Override
        protected Void doInBackground(Goal... goals) {
            asyncTaskDao.insert(goals[0]);
            return null;
        }
    }

    public void deleteAll() {new deleteAllAsyncTask(goalDao).execute();}

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private GoalDao asyncTaskDao;

        public deleteAllAsyncTask(GoalDao dao) {asyncTaskDao = dao;}

        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.deleteAll();
            return null;
        }
    }

    public void delete(Goal goal) {new deleteAsyncTask(goalDao).execute(goal);}

    private static class deleteAsyncTask extends AsyncTask<Goal, Void, Integer> {
        private GoalDao asyncTaskDao;

        public deleteAsyncTask(GoalDao dao) {asyncTaskDao = dao;}

        @Override
        protected Integer doInBackground(Goal... goals) {
            int updatedIndex;
            updatedIndex = asyncTaskDao.deleteAGoal(goals[0].getId());
            //Auto-boxing operation
            return updatedIndex;
        }
    }

    /**
     * Match the goal in the database with the same id
     * Then using update the database with the new goal
     * @param goal with id to be updated
     */
    public void update(Goal goal) {new updateAsyncTask(goalDao).execute(goal);}

    private static class updateAsyncTask extends AsyncTask<Goal, Void, Void> {

        private GoalDao asyncTaskDao;

        public updateAsyncTask(GoalDao dao) {asyncTaskDao = dao;}

        @Override
        protected Void doInBackground(Goal... goals) {
            asyncTaskDao.updateGoal(goals[0].getId(),
                    goals[0].getGoalName(),
                    goals[0].isMoreThanValue(),
                    goals[0].getValue(),
                    goals[0].getFrequency(),
                    goals[0].getDefaultvalue());
            return null;
        }
    }
}
