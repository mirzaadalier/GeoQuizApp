package com.example.a20220305018_app1;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public ResultsAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.result_item, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        // Veritabanından verileri al
        String name = mCursor.getString(mCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
        int age = mCursor.getInt(mCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AGE));
        int score = mCursor.getInt(mCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SCORE));
        String date = mCursor.getString(mCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));

        // Verileri ilgili TextView'lere ata
        holder.nameTextView.setText("Name: " + name);
        holder.ageTextView.setText("Age: " + age);
        holder.scoreTextView.setText("Score: " + score);
        holder.dateTextView.setText("Date: " + date);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView ageTextView;
        public TextView scoreTextView;
        public TextView dateTextView;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            ageTextView = itemView.findViewById(R.id.ageTextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}
