package com.example.goaltracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.goaltracker.adapter.GoalRViewAdapter_Main;
import com.example.goaltracker.model.Goal;
import com.example.goaltracker.model.MainViewModel;
import com.example.goaltracker.model.Record;
import com.example.goaltracker.util.Constants;
import com.example.goaltracker.util.DateTimeHandler;
import com.example.goaltracker.util.GoalBundleHandler;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements UpdateGoalDialogFragment.UpdateGoalDialogListener {

    private static final String TAG = "Main Activity";
    //Interact with Database
    private MainViewModel mainViewModel;

    //Recycler View Component
    private GoalRViewAdapter_Main goalRViewAdapter;
    private RecyclerView recyclerView;

    //Three main buttons
    private ImageButton addGoal;
    private ImageButton addRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup Recycler View
        recyclerView = findViewById(R.id.main_act_rview);
        goalRViewAdapter = new GoalRViewAdapter_Main(this);
        goalRViewAdapter.setLifecycleOwner(this);
        goalRViewAdapter.setGoalRViewAdapterListener(new GoalRViewAdapter_Main.GoalRViewAdapterListener() {
            @Override
            public void onGoalClick(Goal goal) {
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                Bundle args = GoalBundleHandler.putGoalInBundle(goal);
                Log.d(TAG, "onGoalClick: " + args);
                intent.putExtras(args);
                startActivity(intent);
            }

            @Override
            public void onEditClick(Goal goal) {
                showUpdateGoalDialog(goal);
            }

            @Override
            public void onDeleteClick(Goal goal) {
                mainViewModel.delete(goal);
            }

        });



        recyclerView.setAdapter(goalRViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Interact with Database
        mainViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);
        mainViewModel.getAllGoal().observe(this, new Observer<List<Goal>>() {
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
                Intent intent = new Intent(MainActivity.this, AddRecordActivity.class);
                startActivityForResult(intent, Constants.MAIN_ADD_RECORD_REQUESTCODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.MAIN_ADD_RECORD_REQUESTCODE) {
            if (resultCode == RESULT_OK) {
                assert data != null : "Nothing is passed from AddRecordActivity";
                String msg = data.getStringExtra(Constants.MAIN_ADD_RECORD_MSGNAME);
                assert msg != null : "Nothing is passed from AddRecordActivity";
                Snackbar.make(recyclerView, msg, Snackbar.LENGTH_SHORT)
                        .show();
            }
        }

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

        Bundle args = GoalBundleHandler.putGoalInBundle(goal);

        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "AddGoalDialog");
    }


    @Override
    public void onClickUpdateGoalDialog(UpdateGoalDialogFragment dialog, Goal toUpdate, boolean update) {
        String text;
        if (update) {
            mainViewModel.update(toUpdate);
            text = "Update Saved!";
        } else {
            mainViewModel.insert(toUpdate);
            text = "New Habit Saved!";
        }
        dialog.dismiss();
        Snackbar.make(recyclerView, text, Snackbar.LENGTH_SHORT)
                .show();
    }
}