package com.example.goaltracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.goaltracker.model.Goal;
import com.example.goaltracker.util.Constants;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoalDialogFragment extends DialogFragment implements View.OnClickListener {

    public static final String TAG = "GoalDialogFragment";

    View view;

    EditText goalName;
    protected final List<String> MORE_THAN_OPTION = new ArrayList<String>(
            Arrays.asList("More Than", "Less Than"));

    Spinner moreThan;
    final String[] moreThanSelection = new String[1];

    EditText value;

    RadioButton daily;
    RadioButton weekly;
    RadioButton monthly;
    String frequencySelection;

    EditText defaultval;

    Button saveButton;

    GoalDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_add_new_goal, null);

        //Set Up Goal Name
        goalName = view.findViewById(R.id.dialog_add_new_goal_goalname);

        //Set Up Target-More Than
        moreThan = view.findViewById(R.id.dialog_add_new_goal_morethan);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item,
                MORE_THAN_OPTION);
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
        //TODO: Dive to pressing mechanism of the button
        daily = view.findViewById(R.id.dialog_add_new_goal_daily);
        daily.setOnClickListener(this);

        weekly = view.findViewById(R.id.dialog_add_new_goal_weekly);
        weekly.setOnClickListener(this);

        monthly = view.findViewById(R.id.dialog_add_new_goal_monthly);
        monthly.setOnClickListener(this);

        //Set Up Default
        defaultval = view.findViewById(R.id.dialog_add_new_goal_default);



        builder.setView(view);

        return builder.create();
    }

    protected void setUpSaveButton() {
        //Set Up save button
        saveButton = view.findViewById(R.id.dialog_add_new_goal_saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goalName.getText() != null &&
                        moreThanSelection[0] != null &&
                        defaultval.getText() != null &&
                        frequencySelection != null &&
                        value.getText() != null) {
                    String glName = goalName.getText().toString().trim();
                    boolean mrThan = moreThanSelection[0].equals(MORE_THAN_OPTION.get(0));
                    double val = Double.parseDouble(value.getText().toString().trim());
                    String frequency = frequencySelection;
                    double defVal = Double.parseDouble(defaultval.getText().toString().trim());
                    Goal toAdd = new Goal (glName, mrThan, val, frequency, defVal);
                    listener.onClickGoalDialog(GoalDialogFragment.this, toAdd);
                } else {
                    Snackbar.make(v, "Empty Field(s) not Allowed", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });

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

    public interface GoalDialogListener {
        void onClickGoalDialog(GoalDialogFragment dialog, Goal toAdd);
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (GoalDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }

    }


}