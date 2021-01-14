package com.example.fidelitycontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddFidelityCardActivity extends AppCompatActivity {


    public static boolean isUpdate=false;
    public static final String ADD_CARD="adauga card";
    public static int idUpdate;
    private Intent intent;
    EditText etCardNumber;
    private EditText etDataExpirare;
    EditText etProprietar;
    EditText etCompanie;
    EditText etNrPuncte;
    private RadioGroup rg;
    RadioButton rbDa;
    RadioButton rbNu;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fidelity_card);
        intent = getIntent();
        preluareControaleUI();
        rbDa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etDataExpirare.setEnabled(true);
            }
        });
        if(rbNu.isChecked()){
          etDataExpirare.setEnabled(false);
        }
        if(intent.hasExtra(CardListingActivity.EDIT_CARD)){
            isUpdate=true;
            populareCampuri();
        }


       Button btnSalvare = findViewById(R.id.btnSalvareCard);
        btnSalvare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etCardNumber.getText().toString().isEmpty())
                    etCardNumber.setError(getString(R.string.error_card_number));
                else
                if(etCompanie.getText().toString().isEmpty())
                    etCompanie.setError(getString(R.string.error_companie));
                else
                if(etProprietar.getText().toString().isEmpty())
                    etProprietar.setError(getString(R.string.error_proprietar));
                else
                if(etDataExpirare.getText().toString().isEmpty() && etDataExpirare.isEnabled())
                    etDataExpirare.setError(getString(R.string.error_data_expirarii_empty));
                else
                if(etNrPuncte.getText().toString().isEmpty())
                    etNrPuncte.setError(getString(R.string.error_nr_puncte_empty));
                else
                    {
                    Date dataExpirare=null;
                    int nrPct=Integer.parseInt(etNrPuncte.getText().toString());
                    String companie=etCompanie.getText().toString();
                    String proprietar=etProprietar.getText().toString();
                    String cardNo=etCardNumber.getText().toString();
                    RadioButton radioButton=findViewById(rg.getCheckedRadioButtonId());
                    if(radioButton.getText().equals("Da")){
                        //parsare data
                        Locale locale = new Locale("ro", "RO");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy",locale);
                        try {
                           dataExpirare= sdf.parse(etDataExpirare.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    FidelityCard fidelityCard=new FidelityCard(cardNo,nrPct,companie,dataExpirare,proprietar);
                    FidelityCardDB db = FidelityCardDB.getInstanta(getApplicationContext());
                    if(isUpdate==false) {
                        int insertedId = (int) db.getFidelityCardDao().insert(fidelityCard);
                        fidelityCard.setId(insertedId);
                    }
                    else if(isUpdate==true){
                        Log.i("SE-APELEAZA-UPDATE", String.valueOf(isUpdate));
                        fidelityCard.setId(idUpdate);
                        Log.i("ID-UPDATE", String.valueOf(fidelityCard.getId()));
                        db.getFidelityCardDao().update(fidelityCard);
                    }

                    intent.putExtra(ADD_CARD,fidelityCard);
                    setResult(RESULT_OK,intent);
                    finish();
                }

            }
        });

    }
    private void preluareControaleUI(){
        etDataExpirare = findViewById(R.id.editTextDataExpirare);
        rg = findViewById(R.id.radioGroup);
        rbDa = findViewById(R.id.rbDa);
        rbNu = findViewById(R.id.rbNu);
        etCompanie = findViewById(R.id.editTextCompanie);
        etProprietar = findViewById(R.id.editTextProprietar);
        etCardNumber = findViewById(R.id.editTextCardNumber);
        etNrPuncte = findViewById(R.id.editTextNrPuncte);
        tv=findViewById(R.id.tvAdaugaCard);
    }

   private void populareCampuri(){
    FidelityCard card = (FidelityCard)intent.getSerializableExtra(CardListingActivity.EDIT_CARD);
    Log.i("HASEXTRAID", String.valueOf(card.getId()));
    //preluare id element la care se face edit
    idUpdate=card.getId();
    tv.setText(R.string.edit_card);
    etCardNumber.setText(card.getCardNumber());
    etCompanie.setText(card.getCompanie());
    etNrPuncte.setText(String.valueOf(card.getNrPuncte()));
    etProprietar.setText(card.getProprietar());
    if(card.getDataExpirare()!=null){
        rg.check(R.id.rbDa);
        etDataExpirare.setEnabled(true);
        etDataExpirare.setText(card.getDataExpirare().toString());
    }else{
        etDataExpirare.setEnabled(false);
        rg.check(R.id.rbNu);
    }
}

}