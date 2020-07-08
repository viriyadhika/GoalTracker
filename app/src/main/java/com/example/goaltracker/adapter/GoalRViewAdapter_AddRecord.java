package com.example.goaltracker.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goaltracker.R;
import com.example.goaltracker.model.Goal;

import java.util.HashMap;
import java.util.Map;

public class GoalRViewAdapter_AddRecord extends GoalRViewAdapter {

    Map<Goal, Double> values = new HashMap<>();

    public GoalRViewAdapter_AddRecord(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rview_row_add_record, parent, false);

        return new GoalRViewAdapter_AddRecord.ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalRViewAdapter.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        GoalRViewAdapter_AddRecord.ViewHolder holderCasted = (GoalRViewAdapter_AddRecord.ViewHolder) holder;
        EditText value = holderCasted.getValue();
        value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && !s.toString().isEmpty()) {
                    values.put(GoalRViewAdapter_AddRecord.super.getGoal(position),
                            Double.parseDouble(s.toString()));
                } else values.remove(GoalRViewAdapter_AddRecord.super.getGoal(position));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public Map<Goal, Double> getValues() {
        return values;
    }

    private class ViewHolder extends GoalRViewAdapter.ViewHolder {

        private EditText value;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView, context, (TextView) itemView.findViewById(R.id.rview_row_add_record_title));
            value = itemView.findViewById(R.id.rview_row_add_record_valueinput);


        }

        public EditText getValue() {
            return value;
        }

    }
}
