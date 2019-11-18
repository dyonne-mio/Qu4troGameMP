package com.example.qu4trogame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {
    TextView txtWelcome;
    Button btnLogout, btnNewGame, btnQuitGame, btnGameRecords, btnHowToPlay, btnSettings;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        txtWelcome = findViewById(R.id.txtWelcome);

        Intent i = getIntent();
        Bundle extras = i.getExtras();

        user = extras.getString("USER");
        txtWelcome.setText("Welcome " + user + " 🐶!");
        txtWelcome.setTextSize(16);

        btnLogout = findViewById(R.id.btnLogout);
        btnQuitGame = findViewById(R.id.btnQuitGame);
        btnNewGame = findViewById(R.id.btnNewGame);
        btnGameRecords = findViewById(R.id.btnGameRecords);
        btnHowToPlay = findViewById(R.id.btnHowToPlay);
        btnSettings = findViewById(R.id.btnSettings);


    }

    public void newGame(View view){
        Intent intent = new Intent(this, MainGame.class);
        startActivity(intent);
    }

    public void howToPlay(View view){

    }

    public void gameRecords(View view){

    }

    public void settings(View view){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void quitGame(View view){
        finish();
    }

    public void logout(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity (intent);
        finish();
    }
}
