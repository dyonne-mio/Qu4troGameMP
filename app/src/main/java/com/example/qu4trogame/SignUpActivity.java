package com.example.qu4trogame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.os.Debug.waitForDebugger;

public class SignUpActivity extends AppCompatActivity {
    EditText txtEmail, txtUsername, txtPassword, txtConfirmPassword;
    Button  btnSignUp, btnQuitGame;
    TextView txtLogin;
    ProgressBar progressBar;
    public FirebaseAuth auth;
    public FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        txtEmail = findViewById(R.id.txtEmail);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        txtLogin = findViewById(R.id.txtLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnQuitGame = findViewById(R.id.btnQuitGame);
        progressBar = findViewById(R.id.progressBar);
    }

    public void login(View view){
        finish();
    }

    public void signUp(View view){
        String username = txtUsername.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String confirmPassword = txtConfirmPassword.getText().toString();

        //error checking
        if (email.isEmpty()){
            txtEmail.setError("Email is required");
            txtEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txtEmail.setError("Please enter a valid email.");
            txtEmail.requestFocus();
            return;
        }

        if (username.isEmpty()){
            txtUsername.setError("Username is required");
            txtUsername.requestFocus();
            return;
        }

        if (password.isEmpty()){
            txtPassword.setError("Password is required");
            txtPassword.requestFocus();
            return;
        }

        if (password.length()<6){
            txtPassword.setError("Minimum length of password should be 6");
            txtPassword.requestFocus();
            return;
        }

        if (confirmPassword.isEmpty()){
            txtConfirmPassword.setError("Please confirm your password");
            txtConfirmPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)){
            txtPassword.setError("Passwords do not match");
            txtPassword.requestFocus();
            txtConfirmPassword.setError("Passwords do not match");
            txtConfirmPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        //user registration
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "User registration successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "An error has occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void quitGame(View view){
        finish();
    }

    public void back(View view){
        finish();
    }
}
