package com.example.fidelitycontrol;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListareOferteActivity extends AppCompatActivity {
    ListView lvOferte;
    List<Oferta> oferte = new ArrayList<Oferta>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listare_oferte);

        lvOferte=findViewById(R.id.lvOferte);

        FidelityCardDB db = FidelityCardDB.getInstanta(getApplicationContext());
        oferte=db.getOfertaDao().getAll();
        OferteAdapter adapter = new OferteAdapter(getApplicationContext(),R.layout.oferta_lv,oferte,getLayoutInflater()){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view= super.getView(position, convertView, parent);
                Oferta oferta=oferte.get(position);
                TextView tvZile=view.findViewById(R.id.elemNrZile);
                if(oferta.getNrDeZileValabilitate()>5){
                    tvZile.setTextColor(Color.YELLOW);
                }
                else{
                    tvZile.setTextColor(Color.RED);
                }
                return view;
            }
        };
        lvOferte.setAdapter(adapter);
    }

}