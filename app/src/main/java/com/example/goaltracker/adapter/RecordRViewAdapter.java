package com.example.goaltracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goaltracker.R;
import com.example.goaltracker.model.Goal;
import com.example.goaltracker.model.Record;
import com.example.goaltracker.util.DateTimeHandler;

import java.util.List;

public class RecordRViewAdapter extends RecyclerView.Adapter<RecordRViewAdapter.ViewHolder> {

    List<Record> allRecords;

    OnDeleteClickListener listener;

    @NonNull
    @Override
    public RecordRViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rview_row_view_record, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordRViewAdapter.ViewHolder holder, final int position) {
        if (allRecords != null) {
            final Record current = allRecords.get(position);
            holder.dateTextView.setText(DateTimeHandler.longToStringDate(current.getDate()));
            holder.valueTextView.setText(String.valueOf(current.getValue()));
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onDeleteClick(current);
                    }
                }
            });
        } else {
            holder.dateTextView.setText(R.string.rview_row_main_no_record);
        }
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Record record);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.listener = onDeleteClickListener;
    }

    @Override
    public int getItemCount() {
        if (allRecords != null) {
            return allRecords.size();
        } else return 0;
    }

    public void setAllRecords(List<Record> records){
        allRecords = records;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dateTextView;
        public TextView valueTextView;
        public ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTextView = itemView.findViewById(R.id.rview_row_view_record_date);
            valueTextView = itemView.findViewById(R.id.rview_row_view_record_value);
            deleteButton = itemView.findViewById(R.id.rview_row_view_record_delete);

        }
    }

}
