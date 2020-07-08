package com.example.goaltracker.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goaltracker.R;
import com.example.goaltracker.model.Goal;
import com.example.goaltracker.model.MainViewModel;
import com.example.goaltracker.util.Constants;

public class GoalRViewAdapter_Main extends GoalRViewAdapter {
    public static final String TAG = "GoalRViewAdapter_Main";

    private MainViewModel viewModel;
    private Activity owner;
    private GoalRViewAdapterListener goalRViewAdapterListener;

    public GoalRViewAdapter_Main(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rview_row_main, parent, false);

        viewModel = ViewModelProviders.of((FragmentActivity) parent.getContext())
                .get(MainViewModel.class);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalRViewAdapter.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final GoalRViewAdapter_Main.ViewHolder castedHolder = (GoalRViewAdapter_Main.ViewHolder) holder;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goalRViewAdapterListener != null) {
                    goalRViewAdapterListener.onGoalClick(GoalRViewAdapter_Main.super.getGoal(position));
                }
            }
        });

        castedHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goalRViewAdapterListener != null) {
                    goalRViewAdapterListener.onDeleteClick(GoalRViewAdapter_Main.super.getGoal(position));
                }
            }
        });

        castedHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goalRViewAdapterListener != null) {
                    goalRViewAdapterListener.onEditClick(GoalRViewAdapter_Main.super.getGoal(position));
                }
            }
        });


        viewModel.getProgressBar(GoalRViewAdapter_Main.super.getGoal(position), (LifecycleOwner) owner)
                .observe((LifecycleOwner) owner,
                        new Observer<Double>() {
                    @Override
                    public void onChanged(Double value) {
                        castedHolder.progressBar.setProgress((int) (value / GoalRViewAdapter_Main.super.getGoal(position).getValue() * 100));
                        String text;
                        switch (GoalRViewAdapter_Main.super.getGoal(position).getFrequency()){
                            case Constants.DAILY:
                                text = "today";
                                break;
                            case Constants.WEEKLY:
                                text = "this week";
                                break;
                            case Constants.MONTHLY:
                                text = "this month";
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + GoalRViewAdapter_Main.super.getGoal(position).getFrequency());
                        }

                        castedHolder.progressText.setText(String.valueOf(
                                "Total for " + text + ": " +
                                value)
                        );
                    }
        });


        String moreThanValue;
        if (GoalRViewAdapter_Main.super.getGoal(position).isMoreThanValue()) {
            moreThanValue = Constants.MORE_THAN_OPTION.get(0);
        } else {
            moreThanValue = Constants.MORE_THAN_OPTION.get(1);
        }

        castedHolder.targetText.setText(String.valueOf(
                "Target: "
                + moreThanValue + " "
                + GoalRViewAdapter_Main.super.getGoal(position).getValue() + " "
                + GoalRViewAdapter_Main.super.getGoal(position).getFrequency()
        ));

    }

    public interface GoalRViewAdapterListener {
        void onGoalClick(Goal goal);
        void onEditClick(Goal goal);
        void onDeleteClick(Goal goal);
        LiveData<Integer> getProgressBar(Goal goal);
    }

    public void setGoalRViewAdapterListener (GoalRViewAdapterListener goalViewAdapterListener) {
        this.goalRViewAdapterListener = goalViewAdapterListener;
    }

    public void setLifecycleOwner(Activity owner) {this.owner = owner;}

    private static class ViewHolder extends GoalRViewAdapter.ViewHolder {

        public ImageButton deleteButton;
        public ImageButton editButton;
        public TextView targetText;
        public ProgressBar progressBar;
        public TextView progressText;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView, context, (TextView) itemView.findViewById(R.id.rview_row_main_title));
            deleteButton = itemView.findViewById(R.id.rview_row_main_delete);
            editButton = itemView.findViewById(R.id.rview_row_main_update);
            targetText = itemView.findViewById(R.id.rview_row_main_target);
            progressBar = itemView.findViewById(R.id.rview_row_main_progressbar);
            progressText = itemView.findViewById(R.id.rview_row_main_progresstext);
        }

    }


}
