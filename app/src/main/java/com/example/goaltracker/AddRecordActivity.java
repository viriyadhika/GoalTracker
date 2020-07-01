package com.example.goaltracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.goaltracker.adapter.GoalRViewAdapter_AddRecord;
import com.example.goaltracker.adapter.GoalRViewAdapter_Main;
import com.example.goaltracker.model.Goal;
import com.example.goaltracker.model.GoalViewModel;
import com.example.goaltracker.model.Record;
import com.example.goaltracker.model.RecordViewModel;

import java.util.List;

public class AddRecordActivity extends AppCompatActivity {

    private static final String TAG = "Add Record Activity";
    private GoalViewModel goalViewModel;
    private RecordViewModel recordViewModel;

    private GoalRViewAdapter_AddRecord goalRViewAdapter;
    private RecyclerView recyclerView;

    private Button calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        setupRecyclerView();

        //Setup goal viewmodel
        goalViewModel = ViewModelProviders.of(this)
                .get(GoalViewModel.class);
        goalViewModel.getAllGoal().observe(this, new Observer<List<Goal>>() {
            @Override
            public void onChanged(List<Goal> goals) {
                goalRViewAdapter.setGoalList(goals);
            }
        });

        //Setup RecordViewModel
        recordViewModel = ViewModelProviders.of(this)
                .get(RecordViewModel.class);
        recordViewModel.getAllRecord().observe(this, new Observer<List<Record>>() {
            @Override
            public void onChanged(List<Record> records) {
//                Log.d(TAG, "onChanged: " + records.get(0).getValue());
            }
        });
//        Record test = new Record(1, java.lang.System.currentTimeMillis(), 12.5) {};
//        recordViewModel.insert(test);

        //Setup Calendar Button
        //TODO: Implement default is today date
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this);
        calendar = findViewById(R.id.activity_add_record_calendar);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });



    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.add_record_act_rview);
        goalRViewAdapter = new GoalRViewAdapter_AddRecord(this);
        recyclerView.setAdapter(goalRViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}