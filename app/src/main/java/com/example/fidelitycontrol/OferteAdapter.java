package com.example.fidelitycontrol;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.List;


public class OferteAdapter extends ArrayAdapter<Oferta> {

    private Context context;
    private int resource;
    private List<Oferta> oferteList;
    private LayoutInflater layoutInflater;
    public OferteAdapter(@NonNull Context context, int resource, List<Oferta> oferteList, LayoutInflater layoutInflater) {
        super(context, resource,oferteList);
        this.context=context;
        this.resource=resource;
        this.oferteList=oferteList;
        this.layoutInflater=layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view =layoutInflater.inflate(resource,parent,false);
        Oferta oferta=oferteList.get(position);
        if(oferta!=null){
            TextView tvDenumire=view.findViewById(R.id.elemDenumire);
            tvDenumire.setText(oferta.getDenumireOferta());
            TextView tvMesaj=view.findViewById(R.id.elemMesaj);
            tvMesaj.setText(oferta.getMesajOferta());
            TextView tvNr=view.findViewById(R.id.elemNrZile);
            tvNr.setText(String.valueOf(oferta.getNrDeZileValabilitate()));
          }
        return view;
    }
}
