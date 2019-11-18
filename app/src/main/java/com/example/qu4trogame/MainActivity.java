package com.example.qu4trogame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText txtEmail, txtPassword;
    Button btnLogin, btnSignUp, btnGuest, btnQuitGame;
    private boolean musicBound=false;
    private BackgroundSoundService musicSrv;
    private Intent playIntent;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static String TAG = "LoginDEBUG";
    private FirebaseAuth auth;
   // Intent svc = new Intent(this, BackgroundSoundService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnGuest = findViewById(R.id.btnGuest);
        btnQuitGame = findViewById(R.id.btnQuitGame);

        //startService(svc);
        //bind service so other activities can access the service
        //bindService(svc,  , Context.BIND_AUTO_CREATE);

        attachService();
        startService(playIntent);
    }

    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BackgroundSoundService.MusicBinder binder = (BackgroundSoundService.MusicBinder)service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.startService(playIntent);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicSrv = null;
            musicBound = false;
        }
    };


    private void attachService() {
        playIntent = new Intent(this, BackgroundSoundService.class);
        bindService(playIntent, musicConnection, Service.BIND_AUTO_CREATE);
    }

    private void detachService() {
        unbindService(musicConnection);
    }

    /** Callback when service attached. */
    protected void onServiceAttached(BackgroundSoundService service) {
        // do something necessary by its subclass.
    }

    public void login(View view){
        final String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        if (email.isEmpty()){
            txtEmail.setError("Please enter your registered email to proceed");
            txtEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txtEmail.setError("Please enter a valid email.");
            txtEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            txtPassword.setError("Please enter your password to proceed");
            txtPassword.requestFocus();
            return;
        }

        //user login

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent intent = new Intent(MainActivity.this, MainMenu.class);
                    Bundle extras = new Bundle();
                    extras.putString("USER", email);

                    intent.putExtras(extras);
                    startActivity(intent);
                }
            }
        });

    }

    public void signUp(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void continueAsGuest(View view){
        /*auth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                }
            }
        });*/
    }

    public void quitGame(View view){
        auth.signOut();
        stopService(playIntent);
        finish();
    }
}
