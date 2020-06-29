package com.example.goaltracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goaltracker.R;
import com.example.goaltracker.model.Goal;

import java.util.List;


public class GoalRecyclerViewAdapter extends RecyclerView.Adapter<GoalRecyclerViewAdapter.ViewHolder> {

    List<Goal> goalList;
    Context context;

    public GoalRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public GoalRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rview_row_main, parent, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalRecyclerViewAdapter.ViewHolder holder, int position) {
        if (goalList != null) {
            Goal current = goalList.get(position);
            holder.goalNameTextView.setText(current.getGoal());
        } else {
            holder.goalNameTextView.setText(R.string.no_goal_added);
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



    public class ViewHolder extends RecyclerView.ViewHolder {
        public Context context;
        public TextView goalNameTextView;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            this.context = context;
            this.goalNameTextView = itemView.findViewById(R.id.rview_row_main_title);
        }
    }
}
