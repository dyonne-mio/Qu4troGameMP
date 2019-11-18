package com.example.qu4trogame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Settings extends AppCompatActivity {
    ImageButton btnChangeUsername, btnChangePassword, btnMusic, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        btnChangeUsername = findViewById(R.id.btnChangeUsername);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnMusic = findViewById(R.id.btnMusic);
        btnExit = findViewById(R.id.btnExit);
    }



    public void back(View view){
        finish();
    }

    public void exit(View view){
        finish();
    }

    public void changeUsername(View view){

    }

    public void changePassword(View view){

    }

    public void toggleMusic(View view){
        ImageButton btn = (ImageButton) findViewById(R.id.btnMusic);
        Drawable drawable = btn.getDrawable();
        if (drawable.getConstantState().equals(getResources().getDrawable(R.drawable.duct_tape_music_on).getConstantState())){
            ((ImageButton) view).setImageResource(R.drawable.duct_tape_music_off);

        }
        else{
            ((ImageButton) view).setImageResource(R.drawable.duct_tape_music_on);
            startService(new Intent(this, BackgroundSoundService.class));
        }

    }
}
