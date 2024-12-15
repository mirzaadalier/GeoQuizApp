package com.example.a20220305018_app1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private TextView mQuestionTextView;
    private ImageView mQuestionImageView;
    private DatabaseHelper mDatabaseHelper;

    private String[] mQuestions;
    private boolean[] mAnswers;
    private int[] mImages;

    private int mCurrentIndex = 0;
    private int mScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mQuestionTextView = findViewById(R.id.questionTextView);
        mTrueButton = findViewById(R.id.trueButton);
        mFalseButton = findViewById(R.id.falseButton);
        mQuestionImageView = findViewById(R.id.questionImageView);

        mDatabaseHelper = new DatabaseHelper(this);

        int quizNumber = getIntent().getIntExtra("quizNumber", 1);
        startQuiz(quizNumber);

        mTrueButton.setOnClickListener(v -> checkAnswer(true));
        mFalseButton.setOnClickListener(v -> checkAnswer(false));
    }

    private void startQuiz(int quizNumber) {
        mCurrentIndex = 0;
        mScore = 0;

        if (quizNumber == 1) {
            mQuestions = new String[]{
                    "The picture above is of Burj Khalifa, the tallest tower in the world.",
                    "The Great Wall of China is visible from space.",
                    "The Eiffel Tower is a bridge located in the capital city of France, Paris.",
                    "The Shard in London is one of the tallest glass buildings in the world.",
                    "The Sydney Opera House is located in Sydney, Austria, and is famous for its concert halls.",
                    "The Empire State Building is an Art Deco skyscraper in the Midtown South neighborhood of Queens in New York City.",
                    "The Petronas Towers in Malaysia were once the tallest twin towers in the world.",
                    "The Leaning Tower of Pisa was originally designed to lean.",
                    "The CN Tower in Toronto, Canada, is primarily used as a communications and observation tower.",
                    "The Bosphorus Bridge is the second bridge to be constructed over the Bosphorus."
            };

            mAnswers = new boolean[]{true, false, false, true, false, false, true, false, true, true};

            mImages = new int[]{
                    R.drawable.burc_halife,
                    R.drawable.great_wall,
                    R.drawable.eiffel_tower,
                    R.drawable.shard_london,
                    R.drawable.sydney_opera,
                    R.drawable.empire_state,
                    0, // Petronas Towers için görsel yok
                    R.drawable.pisa,
                    0,
                    0  // Bosphorus Bridge için görsel yok
            };

        } else if (quizNumber == 2) {
            mQuestions = new String[]{
                    "The Amazon River is the longest river in the world.",
                    "The Sahara is the largest desert in the world.",
                    "Mount Everest is the tallest mountain in the world.",
                    "The Great Barrier Reef is located off the coast of Australia.",
                    "Antarctica is the driest continent on Earth.",
                    "The Dead Sea is the lowest point on Earth's surface.",
                    "The Amazon rainforest is located in Africa.",
                    "The Pacific Ocean is the largest ocean on Earth.",
                    "The Gobi Desert is located in South America.",
                    "The Rocky Mountains are located in Europe."
            };

            mAnswers = new boolean[]{false, true, true, true, true, true, false, true, false, false};

            mImages = new int[]{
                    R.drawable.amazon_river,
                    R.drawable.sahara,
                    R.drawable.everest,
                    R.drawable.barrier_reef,
                    0, // Antarctica için görsel yok
                    0, // Dead Sea için görsel yok
                    0, // Amazon rainforest için görsel yok
                    0, // Pacific Ocean için görsel yok
                    0, // Gobi Desert için görsel yok
                    0  // Rocky Mountains için görsel yok
            };
        }

        updateQuestion();
        mTrueButton.setEnabled(true);
        mFalseButton.setEnabled(true);
    }

    private void updateQuestion() {
        if (mCurrentIndex < mQuestions.length) {
            mQuestionTextView.setText(mQuestions[mCurrentIndex]);

            if (mImages[mCurrentIndex] != 0) {
                mQuestionImageView.setVisibility(View.VISIBLE);
                mQuestionImageView.setImageResource(mImages[mCurrentIndex]);
            } else {
                mQuestionImageView.setVisibility(View.GONE);
            }
        } else {
            Toast.makeText(this, "Quiz Finished! Your score: " + mScore, Toast.LENGTH_LONG).show();
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
            saveResult();
            showResultDialog();
        }
    }

    private void checkAnswer(boolean userAnswer) {
        if (mAnswers[mCurrentIndex] == userAnswer) {
            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }

        mCurrentIndex++;
        updateQuestion();
    }

    private void saveResult() {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String playerName = getIntent().getStringExtra("playerName");
        int playerAge = getIntent().getIntExtra("playerAge", 0);

        mDatabaseHelper.insertResult(playerName, playerAge, mScore, currentDate);
        Toast.makeText(this, "Result saved!", Toast.LENGTH_SHORT).show();
    }


    private void showResultDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Quiz Finished")
                .setMessage("Your score: " + mScore)
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_view_results) {
            startActivity(new Intent(this, ResultsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
