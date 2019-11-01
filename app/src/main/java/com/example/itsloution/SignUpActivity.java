package com.example.itsloution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public boolean testUserName(){

        String username = getUsername();

        if (username.isEmpty() || username.length() < 3 || Character.isDigit(username.charAt(0))){
            emailEditText.setError("Please Enter a Correct UserName");
            requestFocus(emailEditText);
        } else {
            return true;
        }
        return false;
    }

    public boolean testPassword(){

        String password = getPassword();

        if (password.isEmpty() || password.length() < 6 ){
            passwordEditText.setError("Please Enter a Password");
            requestFocus(passwordEditText);
        } else {
            return true;
        }
        return false;
    }
    private void requestFocus(View view){
        if (view.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    public void onClickBtnSignUp(View view) {
        if (testPassword() && testUserName()){
            progressBar = findViewById(R.id.signup_progressBar);
            progressBar.setVisibility(View.VISIBLE);
            FirebaseAuth Auth = FirebaseAuth.getInstance();
            Auth.createUserWithEmailAndPassword(getUsername() , getPassword())
                    .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.i("onComplete:" , String.valueOf(task.isSuccessful()));
                    progressBar.setVisibility(View.GONE);

                    if (task.isSuccessful()){
                        startActivity(new Intent(SignUpActivity.this , UserActivity.class));
                        finish();
                    } else {
                        Log.i("TaskFailed" , String.valueOf(task.getException()));
                    }
                }
            });
            Toast.makeText(getApplicationContext() , "Successfully Sign Up" , Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext() , "Sign Up Failed" , Toast.LENGTH_LONG).show();
        }
    }

    public void onClickBtnSignIn(View view) {
        Intent intent = new Intent(this , LogInActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onResume(){
        super.onResume();
        progressBar = findViewById(R.id.signup_progressBar);
        progressBar.setVisibility(View.GONE);
    }

    public String getUsername(){
        emailEditText = findViewById(R.id.input_email);
        String username = emailEditText.getText().toString().trim();
        return username;
    }

    public String getPassword(){
        passwordEditText = findViewById(R.id.input_password);
        String password = passwordEditText.getText().toString().trim();
        return password;
    }
}
