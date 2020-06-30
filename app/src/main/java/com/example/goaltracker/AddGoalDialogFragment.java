package com.example.goaltracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
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

public class AddGoalDialogFragment extends DialogFragment {

    EditText goalName;
    private final List<String> MORE_THAN_OPTION = new ArrayList<String>(
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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_new_goal, null);

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

        daily = view.findViewById(R.id.dialog_add_new_goal_daily);
        daily.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                assignfreq(Constants.DAILY);
            }
        });
        weekly = view.findViewById(R.id.dialog_add_new_goal_weekly);
        weekly.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                assignfreq(Constants.WEEKLY);
            }
        });
        monthly = view.findViewById(R.id.dialog_add_new_goal_monthly);
        weekly.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                assignfreq(Constants.MONTHLY);
            }
        });

        //Set Up Default
        defaultval = view.findViewById(R.id.dialog_add_new_goal_default);

        //Set Up save button
        saveButton = view.findViewById(R.id.dialog_add_new_goal_saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Make a goal object
                //TODO: Pass the result to onClickAddGoalDialog
                if (goalName.getText() != null &&
                moreThanSelection[0] != null &&
                defaultval.getText() != null &&
                frequencySelection != null &&
                value.getText() != null) {
                    String glName = goalName.getText().toString();
                    boolean mrThan = moreThanSelection[0].equals(MORE_THAN_OPTION.get(0));
                    double val = Double.parseDouble(value.getText().toString());
                    String frequency = frequencySelection;
                    double defVal = Double.parseDouble(defaultval.getText().toString());
                    Goal toAdd = new Goal (glName, mrThan, val, frequency, defVal);
                    listener.onClickAddGoalDialog(AddGoalDialogFragment.this, toAdd);
                } else {
                    Snackbar.make(v, "Empty Field(s) not Allowed", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });

        builder.setView(view);

        return builder.create();
    }

    private void assignfreq(String selection) {
        frequencySelection = selection;
    }

    public interface AddGoalDialogListener {
        void onClickAddGoalDialog(AddGoalDialogFragment dialog, Goal toAdd);
    }

    AddGoalDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (AddGoalDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }

    }

}
