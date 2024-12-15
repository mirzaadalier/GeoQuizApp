package com.example.a20220305018_app1;

import android.os.Bundle;
import android.database.Cursor;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.xml.transform.Result;

public class ResultsActivity extends AppCompatActivity {

    private RecyclerView mResultsRecyclerView;
    private ResultsAdapter mAdapter;
    private DatabaseHelper mDatabaseHelper;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        mResultsRecyclerView = findViewById(R.id.resultsRecyclerView);
        mResultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabaseHelper = new DatabaseHelper(this);

        // Veritabanından sonuçları al
        mCursor = mDatabaseHelper.getAllResults();

        // RecyclerView'a verileri bağla
        mAdapter = new ResultsAdapter(this, mCursor);
        mResultsRecyclerView.setAdapter(mAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCursor != null) {
            mCursor.close(); // Cursor'ı kapatmayı unutma!
        }
    }
}
