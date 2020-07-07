package com.example.goaltracker.util;

import android.os.Bundle;

import com.example.goaltracker.model.Goal;

public class GoalBundleHandler {

    public static Bundle putGoalInBundle (Goal goal) {
        Bundle args = new Bundle();

        args.putInt(Constants.GOAL_ID_COLUMN_NAME, goal.getId());
        args.putString(Constants.GOAL_NAME_COLUMN_NAME, goal.getGoalName());
        args.putBoolean(Constants.GOAL_TARGET_COLUMN_NAME, goal.isMoreThanValue());
        args.putDouble(Constants.GOAL_VALUE_COLUMN_NAME, goal.getValue());
        args.putString(Constants.GOAL_FREQUENCY_NAME, goal.getFrequency());

        return args;
    }

    public static Goal getGoalFromBundle (Bundle args) {

        int id = args.getInt(Constants.GOAL_ID_COLUMN_NAME);
        String name = args.getString(Constants.GOAL_NAME_COLUMN_NAME);
        boolean target = args.getBoolean(Constants.GOAL_TARGET_COLUMN_NAME);
        double value = args.getDouble(Constants.GOAL_VALUE_COLUMN_NAME);
        String frequency = args.getString(Constants.GOAL_FREQUENCY_NAME);

        Goal goal = new Goal(name, target, value, frequency);
        goal.setId(id);
        return goal;
    }


}
