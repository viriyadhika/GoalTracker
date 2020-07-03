package com.example.goaltracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.goaltracker.model.GoalViewModel;
import com.example.goaltracker.model.RecordViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    BarChart chart;

    List<BarEntry> entries = new ArrayList<>();

    GoalViewModel goalViewModel;

    RecordViewModel recordViewModel;

    List<Integer> x = Arrays.asList(1, 2, 3);
    List<Integer> y = Arrays.asList(2, 4, 6);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        chart = findViewById(R.id.activity_statistics_chart);

        for (Integer i : Arrays.asList(0,1,2)) {
            entries.add(new BarEntry (x.get(i), y.get(i)));
        }

        BarDataSet dataSet = new BarDataSet(entries, "a Label");

        BarData barData = new BarData(dataSet);
        chart.setData(barData);
        chart.setTouchEnabled(false);
        chart.invalidate();

        XAxis xAxis = chart.getXAxis();
        xAxis.setAxisMaximum(6);
    }
}