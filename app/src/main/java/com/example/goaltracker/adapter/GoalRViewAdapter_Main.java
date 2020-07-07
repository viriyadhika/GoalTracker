package com.example.goaltracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goaltracker.R;
import com.example.goaltracker.util.Constants;

public class GoalRViewAdapter_Main extends GoalRViewAdapter {
    public static final String TAG = "GoalRViewAdapter_Main";

    private OnGoalClickListener onGoalClickListener;
    private OnDeleteClickListener onDeleteClickListener;
    private OnEditClickListener onEditClickListener;

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

    @Override
    public void onBindViewHolder(@NonNull GoalRViewAdapter.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        GoalRViewAdapter_Main.ViewHolder castedHolder = (GoalRViewAdapter_Main.ViewHolder) holder;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGoalClickListener != null) {
                    onGoalClickListener.onGoalClick(position);
                }
            }
        });

        castedHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteClickListener != null) {
                    onDeleteClickListener.onDeleteClick(position);
                }
            }
        });

        castedHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEditClickListener != null) {
                    onEditClickListener.onEditClick(position);
                }
            }
        });

        String moreThanValue;
        if (goalList.get(position).isMoreThanValue()) {
            moreThanValue = Constants.MORE_THAN_OPTION.get(0);
        } else {
            moreThanValue = Constants.MORE_THAN_OPTION.get(1);
        }

        castedHolder.targetText.setText(String.valueOf(
                "Target: "
                + moreThanValue + " "
                + goalList.get(position).getValue() + " "
                + goalList.get(position).getFrequency()
        ));

    }

    public interface OnEditClickListener{
        void onEditClick(int position);
    }

    public void setOnEditClickListener(OnEditClickListener onEditClickListener) {
        this.onEditClickListener = onEditClickListener;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public void setOnDeleteClickListener (OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public interface OnGoalClickListener {
        void onGoalClick(int position);
    }

    public void setOnGoalClickListener(OnGoalClickListener onGoalClickListener) {
        this.onGoalClickListener = onGoalClickListener;
    }

    private static class ViewHolder extends GoalRViewAdapter.ViewHolder {

        public ImageButton deleteButton;
        public ImageButton editButton;
        public TextView targetText;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView, context, (TextView) itemView.findViewById(R.id.rview_row_main_title));
            deleteButton = itemView.findViewById(R.id.rview_row_main_delete);
            editButton = itemView.findViewById(R.id.rview_row_main_update);
            targetText = itemView.findViewById(R.id.rview_row_main_target);
        }

    }


}
