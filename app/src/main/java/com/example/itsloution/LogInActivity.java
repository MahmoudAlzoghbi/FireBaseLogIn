package com.example.itsloution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    public ProgressBar progressBar;
    public FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public void onClickBtnSignUp(View view) {
        startActivity(new Intent(this , SignUpActivity.class));
    }

    public void onClickBtnLogIn(View view) {
        if (testPassword() && testUsername()){
            progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            auth = FirebaseAuth.getInstance();

            auth.signInWithEmailAndPassword(getUsername() , getPassword())
                    .addOnCompleteListener(this , new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()){
                                startActivity(new Intent(LogInActivity.this , UserActivity.class));
                            } else {
                                Toast.makeText(LogInActivity.this , task.getException().getMessage() , Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    private String getUsername(){
        EditText editText = findViewById(R.id.username);
        return editText.getText().toString().trim();
    }
    private String getPassword(){
        EditText editText = findViewById(R.id.password);
        return editText.getText().toString().trim();
    }

    private boolean testUsername(){
        if (getUsername().isEmpty() || getUsername().length() < 3){
            EditText editText = findViewById(R.id.username);
            editText.setError("Please Write The Correct Username");
        } else {
            return true;
        }
        return false;
    }
    private boolean testPassword(){
        if (getPassword().isEmpty() || getPassword().length() < 6){
            EditText editText = findViewById(R.id.password);
            editText.setError("Please Write The Correct Password");
        } else {
            return true;
        }
        return false;
    }
    @Override
    public void onResume(){
        super.onResume();
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }
}
