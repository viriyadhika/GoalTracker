package com.example.goaltracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.goaltracker.adapter.GoalRViewAdapter_Main;
import com.example.goaltracker.model.Goal;
import com.example.goaltracker.model.GoalViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";
    private GoalViewModel goalViewModel;

    private GoalRViewAdapter_Main goalRViewAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupRecyclerView();

        goalViewModel = ViewModelProviders.of(this)
                .get(GoalViewModel.class);

        goalViewModel.getAllGoal().observe(this, new Observer<List<Goal>>() {
            @Override
            public void onChanged(List<Goal> goals) {
                goalRViewAdapter.setGoalList(goals);
            }
        });


        goalViewModel.deleteAll();
        Goal test = new Goal("Lose Friend", false, 2, "Monthly");
        goalViewModel.insert(test);


    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.main_act_rview);
        goalRViewAdapter = new GoalRViewAdapter_Main(this);
        recyclerView.setAdapter(goalRViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}