package com.example.goaltracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goaltracker.R;

public class GoalRViewAdapter_Main extends GoalRViewAdapter {
    public GoalRViewAdapter_Main(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rview_row_main, parent, false);

        return new ViewHolder(view, context);
    }




    private static class ViewHolder extends GoalRViewAdapter.ViewHolder {

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView, context, (TextView) itemView.findViewById(R.id.rview_row_main_title));
        }
    }


}
