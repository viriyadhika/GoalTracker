package com.example.goaltracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.goaltracker.model.Goal;
import com.example.goaltracker.model.GoalViewModel;
import com.example.goaltracker.model.Record;
import com.example.goaltracker.model.RecordViewModel;
import com.example.goaltracker.util.Constants;
import com.example.goaltracker.util.DateTimeHandler;
import com.example.goaltracker.util.GoalBundleHandler;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    public static final String TAG = "StatisticsActivity";

    public static final int DATA_DISPLAYED_WEEKLY = 8;

    GoalViewModel goalViewModel;
    RecordViewModel recordViewModel;

    Button viewButton;

    TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        //SetUpDatabase
        goalViewModel = ViewModelProviders.of(this)
                .get(GoalViewModel.class);
        goalViewModel.getAllGoal().observe(this, new Observer<List<Goal>>() {
            @Override
            public void onChanged(List<Goal> goals) {

            }
        });

        recordViewModel = ViewModelProviders.of(this)
                .get(RecordViewModel.class);
        recordViewModel.getAllRecord().observe(this, new Observer<List<Record>>() {
            @Override
            public void onChanged(List<Record> records) {
            }
        });


        //Get the extras passed from prev's activity
        Bundle extras = getIntent().getExtras();
        assert extras != null : TAG + ": No extras found from Intent";
        final Goal goal = GoalBundleHandler.getGoalFromBundle(extras);
        getPastWeekRecords(goal.getId()).observe(this, new Observer<List<Record>>() {
            @Override
            public void onChanged(List<Record> records) {
                displayChart(records);
            }
        });

        //Set Up Title
        titleText = findViewById(R.id.activity_statistics_title);
        titleText.setText(goal.getGoalName());

        //SetUp View Button
        viewButton = findViewById(R.id.activity_statistics_view_button);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticsActivity.this, ViewRecordActivity.class);
                intent.putExtra(Constants.GOAL_ID_COLUMN_NAME, goal.getId());
                startActivity(intent);
            }
        });



    }

    private void displayChart(List<Record> records) {

        BarChart chart;
        List<BarEntry> entries = new ArrayList<>();
        chart = findViewById(R.id.activity_statistics_chart);

        for (Record record : records) {
            int index = (DATA_DISPLAYED_WEEKLY - 1) - DateTimeHandler.countDaysBetween(record.getDate(),
                    DateTimeHandler.getCalendarLong(DateTimeHandler.NOW));
            entries.add(new BarEntry((float) index, (float) record.getValue()));
        }

        final List<String> labels = DateTimeHandler.generateDaysFrom(
                DateTimeHandler.getCalendarLong(DateTimeHandler.LAST_WEEK),
                DateTimeHandler.getCalendarLong(DateTimeHandler.NOW));


        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return labels.get((int) value);
            }
        };


        BarDataSet dataSet = new BarDataSet(entries, "a Label");
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);
        barData.setValueTextSize(10f);


        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        xAxis.setAxisMinimum(-0.5f);
        xAxis.setAxisMaximum((float) (DATA_DISPLAYED_WEEKLY - 0.5));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTypeface(ResourcesCompat.getFont(this, R.font.roboto));

        YAxis right = chart.getAxisRight();
        right.setEnabled(false);

        YAxis left = chart.getAxisLeft();
        left.setTypeface(ResourcesCompat.getFont(this, R.font.roboto));
        left.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        left.setDrawGridLines(false);
        left.setAxisMinimum(0f);

        chart.setData(barData);
        chart.setTouchEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.invalidate();

    }

    private LiveData<List<Record>> getPastWeekRecords(int id) {
        return recordViewModel.getRecord(id,
                DateTimeHandler.getCalendarLong(DateTimeHandler.LAST_WEEK),
                DateTimeHandler.getCalendarLong(DateTimeHandler.NOW));
    }

}