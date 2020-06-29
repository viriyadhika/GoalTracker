package com.example.goaltracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.goaltracker.adapter.GoalRecyclerViewAdapter;
import com.example.goaltracker.model.Goal;
import com.example.goaltracker.model.GoalViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";
    private GoalViewModel goalViewModel;

    private GoalRecyclerViewAdapter goalRecyclerViewAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupRecyclerView();

        goalViewModel = ViewModelProviders.of(this)
                .get(GoalViewModel.class);

        Goal test = new Goal ("Lose Weight");

        goalViewModel.insert(test);

        goalViewModel.getAllGoal().observe(this, new Observer<List<Goal>>() {
            @Override
            public void onChanged(List<Goal> goals) {
                goalRecyclerViewAdapter.setGoalList(goals);
            }
        });



    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.main_act_rview);
        goalRecyclerViewAdapter = new GoalRecyclerViewAdapter(this);
        recyclerView.setAdapter(goalRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}