package com.example.goaltracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goaltracker.R;

public class GoalRViewAdapter_AddRecord extends GoalRViewAdapter {

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

    private class ViewHolder extends GoalRViewAdapter.ViewHolder {
        public Context context;
        public TextView goalNameTextView;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView, context, (TextView) itemView.findViewById(R.id.rview_row_add_record_title));
        }
    }
}
