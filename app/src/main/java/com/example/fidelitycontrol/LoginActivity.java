package com.example.fidelitycontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Intent intent;
    private Button btnRegister;
    Button btnLogin;
    CheckBox rememberMe;
    EditText etEmail,etPassword;
    private FirebaseAuth auth;
    RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnRegister=findViewById(R.id.btnGoToRegister);
        btnLogin=findViewById(R.id.btnLogin);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        layout=findViewById(R.id.layoutLogin);
        rememberMe=findViewById(R.id.cbRememberMe);
        etEmail=findViewById(R.id.editTextEmail);
        etPassword=findViewById(R.id.editTextPassword);

        SharedPreferences sharedPreferences=getSharedPreferences("AppPrefs",0);
        String mail=sharedPreferences.getString("email","");
        String password=sharedPreferences.getString("passowrd","");
        etEmail.setText(mail);
        etPassword.setText(password);
        auth=FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }
    private void loginUser(){
     String email = etEmail.getText().toString();
     String password = etPassword.getText().toString();
        SharedPreferences sharedPreferences=getSharedPreferences("AppPrefs",0);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        if(rememberMe.isChecked()){
            editor.putString("email",email);
            editor.putString("passowrd",password);
            editor.apply();
        }
        else{
            editor.putString("email","");
            editor.putString("passowrd","");
            editor.apply();
        }
       auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
         @Override
         public void onComplete(@NonNull Task<AuthResult> task) {
             if(task.isSuccessful()){
             Toast.makeText(getApplicationContext(), R.string.login_successfull, Toast.LENGTH_LONG).show();
             Intent intent1=new Intent(getApplicationContext(),CardListingActivity.class);
             startActivity(intent1);

             }else{
                 Log.i("EROARE-LOGIN",task.getException().toString());
                 Toast.makeText(getApplicationContext(), R.string.login_failed, Toast.LENGTH_LONG).show();
                 Snackbar.make(layout, task.getException().getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
             }
         }
     }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Snackbar.make(layout, e.getMessage(), Snackbar.LENGTH_LONG).show();
           }
       });
    }
}