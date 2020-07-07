package com.example.goaltracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.goaltracker.adapter.RecordRViewAdapter;
import com.example.goaltracker.model.Record;
import com.example.goaltracker.model.RecordViewModel;
import com.example.goaltracker.util.Constants;

import java.util.List;

public class ViewRecordActivity extends AppCompatActivity {
    public static final String TAG = "ViewRecordActivity";

    RecordViewModel recordViewModel;

    RecordRViewAdapter recordRViewAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);

        //Get the extras passed from prev's activity
        Bundle extras = getIntent().getExtras();
        assert extras != null : TAG + ": No extras found from Intent";
        final int goalid = extras.getInt(Constants.GOAL_ID_COLUMN_NAME);

        recyclerView = findViewById(R.id.activity_view_record_rview);
        recordRViewAdapter = new RecordRViewAdapter();
        recordRViewAdapter.setOnDeleteClickListener(new RecordRViewAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(Record record) {
                recordViewModel.delete(record);
            }
        });

        recyclerView.setAdapter(recordRViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recordViewModel = ViewModelProviders.of(this)
                .get(RecordViewModel.class);
        recordViewModel.getRecord(goalid).observe(this, new Observer<List<Record>>() {
            @Override
            public void onChanged(List<Record> records) {
                recordRViewAdapter.setAllRecords(records);
            }
        });


    }
}