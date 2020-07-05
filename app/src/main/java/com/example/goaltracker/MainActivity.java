package com.example.goaltracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.goaltracker.adapter.GoalRViewAdapter_Main;
import com.example.goaltracker.model.Goal;
import com.example.goaltracker.model.GoalViewModel;
import com.example.goaltracker.util.Constants;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements UpdateGoalDialogFragment.UpdateGoalDialogListener {

    private static final String TAG = "Main Activity";
    //Interact with Database
    private GoalViewModel goalViewModel;

    //Recycler View Component
    private GoalRViewAdapter_Main goalRViewAdapter;
    private RecyclerView recyclerView;

    //Three main buttons
    private Button addGoal;
    private Button addRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup Recycler View
        recyclerView = findViewById(R.id.main_act_rview);
        goalRViewAdapter = new GoalRViewAdapter_Main(this);
        goalRViewAdapter.setOnGoalClickListener(new GoalRViewAdapter_Main.OnGoalClickListener() {
            @Override
            public void onGoalClick(int position) {
                Goal goal = goalRViewAdapter.getGoal(position);
                //TODO:Implement wait for result
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                intent.putExtra(Constants.GOALID_NAME, goal.getId());
                startActivity(intent);
            }
        });
        goalRViewAdapter.setOnDeleteClickListener(new GoalRViewAdapter_Main.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position) {
                Goal goal = goalRViewAdapter.getGoal(position);
                goalViewModel.delete(goal);
            }
        });
        goalRViewAdapter.setOnEditClickListener(new GoalRViewAdapter_Main.OnEditClickListener() {
            @Override
            public void onEditClick(int position) {
                Goal goal = goalRViewAdapter.getGoal(position);
                showUpdateGoalDialog(goal);
            }
        });

        recyclerView.setAdapter(goalRViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Interact with Database
        goalViewModel = ViewModelProviders.of(this)
                .get(GoalViewModel.class);
        goalViewModel.getAllGoal().observe(this, new Observer<List<Goal>>() {
            @Override
            public void onChanged(List<Goal> goals) {
                goalRViewAdapter.setGoalList(goals);
            }
        });

        //Initiate 3 main buttons
        addGoal = findViewById(R.id.activity_main_add_goal);
        addGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddGoalDialog();
            }
        });

        addRecord = findViewById(R.id.activity_main_add_record);
        addRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Implement wait for result
                Intent intent = new Intent(MainActivity.this, AddRecordActivity.class);
                startActivity(intent);
            }
        });

    }

    private void showAddGoalDialog() {
        UpdateGoalDialogFragment dialog = new UpdateGoalDialogFragment();
        dialog.show(getSupportFragmentManager(), "AddGoalDialog");
    }

    /**
     * Overloaded method to prepopulate the addGoalDialog with info from goal
     * @param goal to pre populate the data
     */
    private void showUpdateGoalDialog(Goal goal) {
        UpdateGoalDialogFragment dialog = new UpdateGoalDialogFragment();


        Bundle args = new Bundle();

        args.putInt(Constants.GOAL_ID_COLUMN_NAME, goal.getId());
        args.putString(Constants.GOAL_NAME_COLUMN_NAME, goal.getGoalName());
        args.putBoolean(Constants.GOAL_TARGET_COLUMN_NAME, goal.isMoreThanValue());
        args.putDouble(Constants.GOAL_VALUE_COLUMN_NAME, goal.getValue());
        args.putString(Constants.GOAL_FREQUENCY_NAME, goal.getFrequency());
        args.putDouble(Constants.GOAL_DEFAULT_COLUMN_NAME, goal.getDefaultvalue());

        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "AddGoalDialog");
    }

    @Override
    public void onClickUpdateGoalDialog(UpdateGoalDialogFragment dialog, Goal toUpdate, boolean update) {
        String text;
        if (update) {
            goalViewModel.update(toUpdate);
            text = "Update Saved";
        } else {
            goalViewModel.insert(toUpdate);
            text = "Goal Saved";
        }
        dialog.dismiss();
        Snackbar.make(recyclerView, text, Snackbar.LENGTH_SHORT)
                .show();
    }
}