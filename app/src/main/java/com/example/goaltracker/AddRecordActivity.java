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
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.goaltracker.adapter.GoalRViewAdapter_AddRecord;

import com.example.goaltracker.exception.DuplicateRecordException;
import com.example.goaltracker.model.Goal;
import com.example.goaltracker.model.GoalViewModel;
import com.example.goaltracker.model.Record;
import com.example.goaltracker.model.RecordViewModel;
import com.example.goaltracker.util.DateTimeHandler;
import com.google.android.material.snackbar.Snackbar;


import java.util.List;
import java.util.Map;

public class AddRecordActivity extends AppCompatActivity  {

    private static final String TAG = "Add Record Activity";

    private GoalViewModel goalViewModel;
    private RecordViewModel recordViewModel;

    private GoalRViewAdapter_AddRecord goalRViewAdapter;
    private RecyclerView recyclerView;

    private Button calendarButton;
    private Button saveButton;

    private EditText calendarEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        //Set up Recycler View
        recyclerView = findViewById(R.id.add_record_act_rview);
        goalRViewAdapter = new GoalRViewAdapter_AddRecord(this);
        recyclerView.setAdapter(goalRViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                //No UI change, so method is empty
            }
        });

        //Setup From Edit Text, prefill with today date
        calendarEditText =findViewById(R.id.activity_add_record_from);
        calendarEditText.setText(DateTimeHandler.getCalendarText(DateTimeHandler.NOW));

        //Setup From Calendar Button
        //TODO: Implement to cannot be later than today, from cannot be after to
        //TODO: Enforce the date formatting if user decide to type themselves
        //TODO: If user type themselves, calendar also pick
        final DatePickerDialog datePickerDialogFrom = new DatePickerDialog(this);
        datePickerDialogFrom.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Plus one is because month start from 0
                String datePicked = DateTimeHandler.getCalendarText(year, month + 1, dayOfMonth);
                if (DateTimeHandler.afterNow(datePicked)) {
                    Snackbar.make(recyclerView, "Cannot Pick Day after Today", Snackbar.LENGTH_SHORT)
                            .show();
                } else {
                    calendarEditText.setText(datePicked);
                }
            }
        });
        calendarButton = findViewById(R.id.activity_add_record_calendar_from);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialogFrom.show();
            }
        });



        //Setup Save Button
        saveButton = findViewById(R.id.activity_add_record_saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFile();
            }
        });


    }

    //TODO: Implement to loop from "From" to "To" date
    //TODO: Ensure no duplicate entry for the same goalId and same date
    //TODO: Implement snackbar when saving, go back to the previous activity
    /**
     * When saving the file, it only detect which goal is filled. Only those got filled will be input to database
     */
    private void saveFile() {
        long startDate = DateTimeHandler.stringToLongDate(calendarEditText.getText().toString().trim());
        double value;
        Record record;
        Goal goal;

        if (!goalRViewAdapter.getValues().isEmpty()) {
            for (Map.Entry<Goal, Double> entry : goalRViewAdapter.getValues().entrySet()) {
                goal = entry.getKey();
                value = entry.getValue();
                record = new Record(goal.getId(), startDate, value);
                Log.d(TAG, "saveFile: " + record);
                try {
                    recordViewModel.insert(record);
                } catch (DuplicateRecordException e) {
                    Snackbar.make(recyclerView, "Duplicate Record Found!", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        }

    }





}