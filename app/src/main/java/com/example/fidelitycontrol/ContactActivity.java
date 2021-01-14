package com.example.fidelitycontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContactActivity extends AppCompatActivity {

    private EditText eSubject;
    private EditText eMsg;
    private EditText eTomail;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        eSubject=findViewById(R.id.EditTextSubiectMail);
        eMsg=findViewById(R.id.EditTextMesajMail);
        eTomail=findViewById(R.id.EditTextToMail);
        btn=findViewById(R.id.btnSendEmail);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_SEND);
                it.putExtra(Intent.EXTRA_EMAIL, new String[]{eTomail.getText().toString()});
                it.putExtra(Intent.EXTRA_SUBJECT,eSubject.getText().toString());
                it.putExtra(Intent.EXTRA_TEXT,eMsg.getText());
                it.setType(getString(R.string.email_msg_type));
                startActivity(Intent.createChooser(it,getString(R.string.choose_app)));
            }
        });

    }
}