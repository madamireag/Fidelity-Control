package com.example.fidelitycontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    private EditText etNume;
    private EditText etPrenume;
    private Button btnRegister;
    private EditText etEmail;
    private EditText etPassword;
    private FirebaseAuth fireBaseAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        preluareControaleUI();
        fireBaseAuth=FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inregistrareUtilizatorNou();
            }
        });
    }
    private void inregistrareUtilizatorNou(){
        progressBar.setVisibility(View.VISIBLE);
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), R.string.enter_email, Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
          return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), R.string.enter_password, Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
              return;
        }
        if(password.length()<6){
            Toast.makeText(getApplicationContext(), R.string.invalid_password, Toast.LENGTH_LONG).show();
            return;
        }
        fireBaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), R.string.reg_successfull, Toast.LENGTH_LONG).show();

                    progressBar.setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(getApplicationContext(), R.string.reg_failed, Toast.LENGTH_LONG).show();
                    Log.i("EROARE",task.getResult().toString());
                    progressBar.setVisibility(View.GONE);
                }
            }
        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String numePrenume = etNume.getText().toString()+" "+etPrenume.getText().toString();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(numePrenume).build();
                authResult.getUser().updateProfile(profileUpdates);
            }
        });

    }
    private void preluareControaleUI(){
        etNume = findViewById(R.id.editTextNume);
        etPrenume = findViewById(R.id.editTextPrenume);
        etEmail = findViewById(R.id.editTextSetEmail);
        etPassword = findViewById(R.id.editTextSetPassword);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar=findViewById(R.id.progressBarRegister);
    }
}