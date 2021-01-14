package com.example.fidelitycontrol;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class FidelityCardAdapter extends ArrayAdapter<FidelityCard> {
    private Context context;
    private int resource;
    private List<FidelityCard> cardList;
    private LayoutInflater layoutInflater;

    public FidelityCardAdapter(@NonNull Context context, int resource,List<FidelityCard> cardList,LayoutInflater layoutInflater) {
        super(context, resource,cardList);
        this.context=context;
        this.resource=resource;
        this.cardList=cardList;
        this.layoutInflater=layoutInflater;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view =layoutInflater.inflate(resource,parent,false);
        FidelityCard fidelityCard=cardList.get(position);
       if(fidelityCard!=null){
           TextView tvCompanie=view.findViewById(R.id.elemCompanie);
           tvCompanie.setText(fidelityCard.getCompanie());
           TextView tvPuncte=view.findViewById(R.id.elemNrPuncte);
           tvPuncte.setText(String.valueOf(fidelityCard.getNrPuncte())+" puncte");
           TextView tvDate=view.findViewById(R.id.elemDataExpirare);
           ProgressBar progressBar=view.findViewById(R.id.progressBarPremium);
           progressBar.setMax(FidelityCard.getMinPointsForPremium());
           progressBar.setProgress(fidelityCard.getNrPuncte());
           if(fidelityCard.getDataExpirare()!=null){
               Locale locale = new Locale("ro", "RO");
               SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy",locale);
               tvDate.setText(sdf.format(fidelityCard.getDataExpirare()));
           }
           else{
               tvDate.setText(R.string.mesaj_no_data_expirare);
           }
       }

        return view;
    }
}
