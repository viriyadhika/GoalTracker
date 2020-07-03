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
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements AddGoalDialogFragment.AddGoalDialogListener {

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


//        goalViewModel.deleteAll();
//        Goal test = new Goal("Lose Friend", false, 2, "Monthly");
//        goalViewModel.insert(test);
    }

    private void showAddGoalDialog() {
        AddGoalDialogFragment dialog = new AddGoalDialogFragment();
        dialog.show(getSupportFragmentManager(), "AddGoalDialog");
    }

    @Override
    public void onClickAddGoalDialog(AddGoalDialogFragment dialog, Goal toAdd) {
        goalViewModel.insert(toAdd);
        dialog.dismiss();
        Snackbar.make(recyclerView, "Goal Saved", Snackbar.LENGTH_SHORT)
                .show();
    }

}