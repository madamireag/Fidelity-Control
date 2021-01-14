package com.example.fidelitycontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    Button btnSignOut;
    TextView tvUserCurent;
    Button btnChangePasswd;
    Button btnSchimbaNume;
    Button btnChangeEmail;
    Button btnStergeCont;
    EditText etChangePasswd;
    EditText etDisplayName;
    EditText etChangeEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        preluareControaleUI();
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user =  firebaseAuth.getCurrentUser();
        if(user!=null){
        tvUserCurent.setText(getString(R.string.logged_in_as)+" "+user.getDisplayName());
        etDisplayName.setText(user.getDisplayName());
        }
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user =  firebaseAuth.getCurrentUser();
                if(user!=null)
                {   user.updateEmail(etChangeEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), R.string.email_schimbat, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), task.getResult().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        btnChangePasswd.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           FirebaseUser user =  firebaseAuth.getCurrentUser();
           if(user!=null){
               if(etChangePasswd.getText().toString().length()>6){
                   user.updatePassword(etChangePasswd.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if (task.isSuccessful()) {
                               Toast.makeText(getApplicationContext(), R.string.parola_schimbata, Toast.LENGTH_SHORT).show();
                               firebaseAuth.signOut();
                           }
                       }
                   });
               }
           }
       }
   });
        btnSchimbaNume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user =  firebaseAuth.getCurrentUser();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(etDisplayName.getText().toString()).build();
                user.updateProfile(profileUpdates);
            }
        });
       btnStergeCont.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FirebaseUser user =  firebaseAuth.getCurrentUser();
               if(user!=null){
                   user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){
                               Toast.makeText(getApplicationContext(), R.string.cont_sters,Toast.LENGTH_LONG).show();
                           }
                       }
                   });
               }
           }
       });
    }
    private void preluareControaleUI(){
        tvUserCurent=findViewById(R.id.tvCurrentUser);
        btnSignOut = findViewById(R.id.btnSignOut);
        etChangePasswd=findViewById(R.id.etChangePassword);
        etChangeEmail=findViewById(R.id.etChangeEmail);
        btnChangeEmail=findViewById(R.id.btnChangeEmail);
        btnChangePasswd=findViewById(R.id.btnChangePassword);
        etDisplayName=findViewById(R.id.etDisplayName);
        btnSchimbaNume=findViewById(R.id.btnChangeName);
        btnStergeCont=findViewById(R.id.btnStergeCont);
    }
}