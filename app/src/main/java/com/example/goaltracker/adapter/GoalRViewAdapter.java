package com.example.goaltracker.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goaltracker.R;
import com.example.goaltracker.model.Goal;

import java.util.List;


public abstract class GoalRViewAdapter extends RecyclerView.Adapter<GoalRViewAdapter.ViewHolder> {

    List<Goal> goalList;
    Context context;

    public GoalRViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public abstract GoalRViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull GoalRViewAdapter.ViewHolder holder, int position) {
        if (goalList != null) {
            Goal current = goalList.get(position);
            holder.goalNameTextView.setText(current.getGoalName());
        } else {
            holder.goalNameTextView.setText(R.string.rview_row_main_no_goal);
        }
    }

    @Override
    public int getItemCount() {
        if (goalList != null) {
            return goalList.size();
        } else return 0;
    }

    public void setGoalList(List<Goal> goals) {
        goalList = goals;
        notifyDataSetChanged();
    }

    public Goal getGoal(int position) {
        return goalList.get(position);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public Context context;
        public TextView goalNameTextView;

        public ViewHolder(@NonNull View itemView, Context context, TextView goalNameTextView) {
            super(itemView);
            this.context = context;
            this.goalNameTextView = goalNameTextView;
        }
    }
}
