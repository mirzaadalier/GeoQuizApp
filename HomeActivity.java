package com.example.a20220305018_app1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private Button btnQuiz1;
    private Button btnQuiz2;
    private String playerName;
    private int playerAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnQuiz1 = findViewById(R.id.btnQuiz1);
        btnQuiz2 = findViewById(R.id.btnQuiz2);

        // Kullanıcıdan isim ve yaş bilgisi almak için dialog göster
        showNameAgeDialog();

        // Buildings Butonuna Tıklama Olayı
        btnQuiz1.setOnClickListener(v -> startQuiz(1));

        // Natural Structures Butonuna Tıklama Olayı
        btnQuiz2.setOnClickListener(v -> startQuiz(2));
    }

    private void showNameAgeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Your Name and Age");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_name_age, null);
        EditText editTextName = dialogView.findViewById(R.id.editTextName);
        EditText editTextAge = dialogView.findViewById(R.id.editTextAge);

        builder.setView(dialogView);

        builder.setPositiveButton("OK", (dialog, which) -> {
            playerName = editTextName.getText().toString().trim();
            String ageText = editTextAge.getText().toString().trim();
            playerAge = ageText.isEmpty() ? 0 : Integer.parseInt(ageText);
            Toast.makeText(this, "Welcome, " + playerName + "!", Toast.LENGTH_SHORT).show();
        });

        builder.setCancelable(false);
        builder.show();
    }

    private void startQuiz(int quizNumber) {
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        intent.putExtra("quizNumber", quizNumber);
        intent.putExtra("playerName", playerName);
        intent.putExtra("playerAge", playerAge);
        startActivity(intent);
    }
}
