package com.example.goaltracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.goaltracker.adapter.GoalRViewAdapter_AddRecord;

import com.example.goaltracker.model.AddRecordViewModel;
import com.example.goaltracker.model.Goal;
import com.example.goaltracker.util.Constants;
import com.example.goaltracker.util.DateTimeHandler;
import com.google.android.material.snackbar.Snackbar;


import java.util.List;

public class AddRecordActivity extends AppCompatActivity  {

    private static final String TAG = "Add Record Activity";

    private AddRecordViewModel addRecordViewModel;

    private GoalRViewAdapter_AddRecord goalRViewAdapter;
    private RecyclerView recyclerView;

    private ImageButton calendarButton;
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



        //Setup From Calendar Button
        final DatePickerDialog datePickerDialogFrom = new DatePickerDialog(this);
        datePickerDialogFrom.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Plus one is because month start from 0
                String datePicked = DateTimeHandler.getCalendarText(year, month + 1, dayOfMonth);
                calendarEditText.setText(datePicked);
                addRecordViewModel.verifyDateFromCalendar(datePicked);
            }
        });
        calendarButton = findViewById(R.id.activity_add_record_calendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialogFrom.show();
            }
        });

        //Setup From Edit Text, prefill with today date
        calendarEditText =findViewById(R.id.activity_add_record_from);
        calendarEditText.setText(DateTimeHandler.getCalendarText(DateTimeHandler.NOW));
        calendarEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    EditText castedView = (EditText) v;
                    if (!castedView.getText().toString().isEmpty()) {
                        String date = castedView.getText().toString();
                        addRecordViewModel.verifyDateFromEditText(date);
                    }
                }
            }
        });


        //Setup Save Button
        saveButton = findViewById(R.id.activity_add_record_saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecord();
            }
        });

        //Setup view model
        addRecordViewModel = ViewModelProviders.of(this)
                .get(AddRecordViewModel.class);
        addRecordViewModel.getAllGoals().observe(this, new Observer<List<Goal>>() {
            @Override
            public void onChanged(List<Goal> goals) {
                goalRViewAdapter.setGoalList(goals);
            }
        });
        addRecordViewModel.setAddRecordViewModelListener(new AddRecordViewModel.AddRecordViewModelListener() {
            @Override
            public void onDateFormatInvalid() {
                calendarEditText.setError("Date format is invalid");
                Snackbar.make(recyclerView, "Date format is invalid", Snackbar.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onDateAfterToday() {
                calendarEditText.setError("Cannot pick a day after today");
                Snackbar.make(recyclerView, "Cannot pick a day after today", Snackbar.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onDateVerifiedFromEditText(String date) {
                datePickerDialogFrom.updateDate(
                        DateTimeHandler.getYear(date),
                        DateTimeHandler.getMonth(date) - 1,
                        DateTimeHandler.getDate(date)
                );
            }

            @Override
            public void onSaveDuplicateFound() {
                Snackbar.make(recyclerView, "Duplicate record found! No record is saved.", Snackbar.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onSaveSuccessful() {
                Intent intent = getIntent();
                intent.putExtra(Constants.MAIN_ADD_RECORD_MSGNAME, "Record saved Successfully");
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }

    /**
     * When saving the file, it only detect which goal is filled. Only those got filled will be input to database
     */
    private void saveRecord() {

        if (!goalRViewAdapter.getValues().isEmpty() && calendarEditText.getText() != null) {
            String dateString = calendarEditText.getText().toString().trim();
            addRecordViewModel.saveRecord(goalRViewAdapter.getValues(), dateString);
        } else {
            Snackbar.make(recyclerView, "Cannot have all empty value. No data to save!", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = getIntent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    //Below Code is for exception handling
    private void showUserDateAfterTodayException(View v) {

    }

    private void showUserDateFormatInvalidException(View v) {

    }






}