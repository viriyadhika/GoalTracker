package com.example.goaltracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.goaltracker.model.Goal;
import com.example.goaltracker.util.Constants;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * UpdateGoal has same structure like AddGoalFragment but it can prepopulate itself
 */
public class UpdateGoalDialogFragment extends DialogFragment implements View.OnClickListener {

    public static final String TAG = "UpdateGoalDialogFragment";

    TextView title;

    EditText goalName;

    Spinner moreThan;
    final String[] moreThanSelection = new String[1];

    EditText value;

    RadioButton daily;
    RadioButton weekly;
    RadioButton monthly;
    String frequencySelection;

    Button saveButton;

    UpdateGoalDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_new_goal, null);

        //Set Up Title
        title = view.findViewById(R.id.dialog_add_new_goal_title);


        //Set Up Goal Name
        goalName = view.findViewById(R.id.dialog_add_new_goal_goalname);

        //Set Up Target-More Than
        moreThan = view.findViewById(R.id.dialog_add_new_goal_morethan);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item,
                Constants.MORE_THAN_OPTION);
        moreThan.setAdapter(adapter);

        moreThan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                moreThanSelection[0] = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Set Up Value
        value = view.findViewById(R.id.dialog_add_new_goal_value);

        //Set Up the 3 Frequency Radio Button
        daily = view.findViewById(R.id.dialog_add_new_goal_daily);
        daily.setOnClickListener(this);

        weekly = view.findViewById(R.id.dialog_add_new_goal_weekly);
        weekly.setOnClickListener(this);

        monthly = view.findViewById(R.id.dialog_add_new_goal_monthly);
        monthly.setOnClickListener(this);

        //If this is an update window, then populate the dialog, otherwise don't do anything
        final boolean updateWindow = getArguments() != null;
        if (updateWindow) {
            prepopulateDialog(getArguments());
        }

        //Set Up save button
        saveButton = view.findViewById(R.id.dialog_add_new_goal_saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!goalName.getText().toString().matches("") &&
                        moreThanSelection[0] != null &&
                        frequencySelection != null &&
                        !value.getText().toString().matches("")) {

                    String glName = goalName.getText().toString().trim();
                    boolean mrThan = moreThanSelection[0].equals(Constants.MORE_THAN_OPTION.get(0));
                    double val = Double.parseDouble(value.getText().toString().trim());
                    String frequency = frequencySelection;

                    Goal toAdd = new Goal (glName, mrThan, val, frequency);
                    if (updateWindow) {
                        int goalId = getArguments().getInt(Constants.GOAL_ID_COLUMN_NAME);
                        toAdd.setId(goalId);
                    }
                    listener.onClickUpdateGoalDialog(UpdateGoalDialogFragment.this, toAdd, updateWindow);
                } else {
                    Snackbar.make(v, "Empty Field(s) not Allowed", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });



        builder.setView(view);

        return builder.create();
    }



    protected void assignfreq(String selection) {
        frequencySelection = selection;
    }

    @Override
    public void onClick(View v) {

        boolean checked = ((RadioButton) v).isChecked();

        switch(v.getId()) {
            case R.id.dialog_add_new_goal_daily:
                if (checked) { assignfreq(Constants.DAILY); }
                break;
            case R.id.dialog_add_new_goal_weekly:
                if (checked) { assignfreq(Constants.WEEKLY); }
                break;
            case R.id.dialog_add_new_goal_monthly:
                if (checked) { assignfreq(Constants.MONTHLY); }
                break;
        }
    }

    public interface UpdateGoalDialogListener {
        void onClickUpdateGoalDialog(UpdateGoalDialogFragment dialog, Goal toAdd, boolean update);
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (UpdateGoalDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }

    }

    private void prepopulateDialog(Bundle arguments) {


        String columnName =  arguments.getString(Constants.GOAL_NAME_COLUMN_NAME);
        boolean target = arguments.getBoolean(Constants.GOAL_TARGET_COLUMN_NAME);
        double val = arguments.getDouble(Constants.GOAL_VALUE_COLUMN_NAME);
        String freq = arguments.getString(Constants.GOAL_FREQUENCY_NAME);

        title.setText(columnName);

        goalName.setText(columnName);

        if (target == true) {
            moreThan.setSelection(0);
        } else {
            moreThan.setSelection(1);
        }

        value.setText(String.valueOf(val));

        switch (freq) {
            case Constants.DAILY:
                daily.performClick();
                break;
            case Constants.WEEKLY:
                weekly.performClick();
                break;
            case Constants.MONTHLY:
                monthly.performClick();
                break;
        }

    }

}
