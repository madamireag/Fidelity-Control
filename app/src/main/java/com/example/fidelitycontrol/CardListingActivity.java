package com.example.fidelitycontrol;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CardListingActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_ADD =200;
    public static final int REQUEST_CODE_EDIT=300;
    public static final String EDIT_CARD="editCard";
    public int poz;
    private Intent intent;
    private FloatingActionButton floatingActionButton;
    private ListView listView;
    private LinearLayout layout;

    List<FidelityCard> fidelityCards=new ArrayList<FidelityCard>();
    List<Oferta> listOferte=new ArrayList<Oferta>();
    FidelityCardDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_listing);

        floatingActionButton = findViewById(R.id.btnAddCard);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFidelityCardActivity.isUpdate=false;
                intent=new Intent(getApplicationContext(),AddFidelityCardActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });
        listView = findViewById(R.id.idlistView);

        registerForContextMenu(listView);

        layout=findViewById(R.id.layout);

        db = FidelityCardDB.getInstanta(getApplicationContext());
        fidelityCards= db.getFidelityCardDao().getAll();
        preluareMinPointsPerPremiumDinSharedPrefs();

    }

    private void setariBackGroundSharedPref(LinearLayout layout){
        if(layout!=null){
         boolean schimbCuloare= PreferenceManager.getDefaultSharedPreferences(this).getBoolean("aplicCuloareFundal",false);
        if(schimbCuloare ==true) {
           layout.setBackgroundColor(Color.parseColor(PreferenceManager.getDefaultSharedPreferences(this).getString("pref_color", "#111111")));
        }
        }
    }

    private void preluareMinPointsPerPremiumDinSharedPrefs(){
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        String Points = sharedPreferences.getString("etMinPoints","1000");
        FidelityCard.setMinPointsForPremium(Integer.valueOf(Points));
        FidelityCardAdapter adapter = new FidelityCardAdapter(getApplicationContext(), R.layout.element_list_view,
                fidelityCards, getLayoutInflater()){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view= super.getView(position, convertView, parent);
                FidelityCard card=fidelityCards.get(position);
                TextView tvNrPuncte=view.findViewById(R.id.elemNrPuncte);
                if(card.getNrPuncte()>= FidelityCard.getMinPointsForPremium()){
                    tvNrPuncte.setTextColor(Color.YELLOW);
                    tvNrPuncte.setText(fidelityCards.get(position).getNrPuncte()+" puncte Premium");
                }
                else{
                    tvNrPuncte.setTextColor(Color.RED);
                }
                return view;
            }
        };
        listView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        setariBackGroundSharedPref(layout);
        preluareMinPointsPerPremiumDinSharedPrefs();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
          case R.id.optiuneContactUs:
                Intent intent = new Intent(this,ContactActivity.class);
                startActivity(intent);
                break;
            case R.id.optiuneRateUs:
                Intent intent1 = new Intent(getApplicationContext(),RatingActivity.class);
                startActivity(intent1);
                break;
            case R.id.optiuneUser:
                Intent intentUser = new Intent(getApplicationContext(),UserActivity.class);
                startActivity(intentUser);
                break;
            case R.id.optiuneSetari:
                Intent intent2 = new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(intent2);
                break;
            case R.id.optiuneClearDB:
                db.getFidelityCardDao().deleteAll();
                AdapterCarduriLV();
                break;
            case R.id.optiuneListaOferte:
                Intent intent3 = new Intent(getApplicationContext(),ListareOferteActivity.class);
                startActivity(intent3);
                break;
            case R.id.optiuneOferteJSON:
                final ExtractOferteJSON extractOferteJSON=new ExtractOferteJSON(){
                    @Override
                    protected void onPostExecute(String s) {
                       listOferte.addAll(ExtractOferteJSON.listaOferte);
                    }
                };
                try {
                    extractOferteJSON.execute(new URL("https://pastebin.com/raw/GAM8pEt4"));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
           case R.id.optiuneJSON:
                        final ExtractCardsJSON extractCardsJSON=new ExtractCardsJSON(){
                    @Override
                    protected void onPostExecute(String s) {
                       // super.onPostExecute(s);
                        String toastString = ExtractCardsJSON.Titlu+" "+ ExtractCardsJSON.DataExtragere;
                        Toast.makeText(getApplicationContext(),toastString,Toast.LENGTH_LONG).show();
                        fidelityCards.addAll(ExtractCardsJSON.listaCarduri);
                        db.getFidelityCardDao().deleteAll();
                        for(FidelityCard f : fidelityCards){
                           int id=(int) db.getFidelityCardDao().insert(f);
                           f.setId(id);
                        }
                        AdapterCarduriLV();
                    }
                };
                try {
                    extractCardsJSON.execute(new URL("https://pastebin.com/raw/T8NPNMUT"));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return true;
        }
        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ctxedit:
                AddFidelityCardActivity.isUpdate=true;
                final AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                final FidelityCardAdapter adaptor=(FidelityCardAdapter) listView.getAdapter();
                poz = menuInfo.position;
                intent = new Intent(getApplicationContext(), AddFidelityCardActivity.class);
                intent.putExtra(EDIT_CARD, adaptor.getItem(menuInfo.position));
                Log.i("ID-EXTRA", String.valueOf(adaptor.getItem(menuInfo.position).getId()));
                startActivityForResult(intent, REQUEST_CODE_EDIT);
                break;
            case R.id.ctxSetareOferte:
                final AdapterView.AdapterContextMenuInfo menuAdd=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                final FidelityCardAdapter adaptor2=(FidelityCardAdapter) listView.getAdapter();
                poz = menuAdd.position;
                int id=adaptor2.getItem(poz).getId();
                for(Oferta of : listOferte){
                    of.setCardId(id);
                    Log.i("ID_CARDURI", String.valueOf(of.getCardId()));
                    of.setId((int)db.getOfertaDao().insert(of));
                }
                break;
            case R.id.ctxStergeOferte:
                db.getOfertaDao().deleteAll();
                Toast.makeText(getApplicationContext(), R.string.oferte_sterse,Toast.LENGTH_LONG).show();
                break;
            case R.id.ctxdelete:
                final AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                final FidelityCardAdapter adapter=(FidelityCardAdapter) listView.getAdapter();
                AlertDialog dialog=new AlertDialog.Builder(CardListingActivity.this)
                        .setTitle(R.string.confirmare_stergere)
                        .setMessage(R.string.delete_msg)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                Toast.makeText(getApplicationContext(), R.string.nimic_sters,Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();
                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                FidelityCardDB db = FidelityCardDB.getInstanta(getApplicationContext());
                                db.getFidelityCardDao().deleteFidelityCard(adapter.getItem(info.position));
                                adapter.remove(adapter.getItem(info.position));
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), R.string.card_sters,Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();
                            }
                        }).create();
                dialog.show();
                return true;
        }
     return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== REQUEST_CODE_ADD && resultCode == RESULT_OK && data!=null){
            FidelityCard fidelityCard=(FidelityCard) data.getSerializableExtra(AddFidelityCardActivity.ADD_CARD);
            if(fidelityCard!=null){
                fidelityCards.add(fidelityCard);
                AdapterCarduriLV();
            }
        }
        else if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK && data != null) {
            FidelityCard fc = (FidelityCard) data.getSerializableExtra(AddFidelityCardActivity.ADD_CARD);
            {
            if(fc!=null){
                fidelityCards.get(poz).setCardNumber(fc.getCardNumber());
                fidelityCards.get(poz).setCompanie(fc.getCompanie());
                fidelityCards.get(poz).setProprietar(fc.getProprietar());
                fidelityCards.get(poz).setNrPuncte(fc.getNrPuncte());
                fidelityCards.get(poz).setId(fc.getId());
                if(fc.getDataExpirare()!=null){
                    fidelityCards.get(poz).setDataExpirare(fc.getDataExpirare());
                }

                FidelityCardAdapter adapter=new FidelityCardAdapter(getApplicationContext(),R.layout.element_list_view,fidelityCards,getLayoutInflater()){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view= super.getView(position, convertView, parent);
                        FidelityCard card=fidelityCards.get(position);
                        TextView tvNrPuncte = view.findViewById(R.id.elemNrPuncte);
                        if(card.getNrPuncte() >= 1000){
                            tvNrPuncte.setTextColor(Color.YELLOW);
                            tvNrPuncte.setText(fidelityCards.get(position).getNrPuncte()+" puncte Premium");
                        }
                        else{
                            tvNrPuncte.setTextColor(Color.RED);
                        }
                        return view;
                    }
                };
                listView.setAdapter(adapter);
                   adapter.notifyDataSetChanged();
            }

        }
        }

    }

  private void AdapterCarduriLV(){
      FidelityCardAdapter adapter=new FidelityCardAdapter(getApplicationContext(),R.layout.element_list_view,fidelityCards,getLayoutInflater()){
          @NonNull
          @Override
          public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
              View view= super.getView(position, convertView, parent);
              FidelityCard card=fidelityCards.get(position);
              TextView tvNrPuncte = view.findViewById(R.id.elemNrPuncte);
              if(card.getNrPuncte() >= 1000){
                  tvNrPuncte.setTextColor(Color.YELLOW);
                  tvNrPuncte.setText(fidelityCards.get(position).getNrPuncte()+" puncte Premium");
              }
              else{
                  tvNrPuncte.setTextColor(Color.RED);
              }
              return view;
          }
      };
      listView.setAdapter(adapter);

  }
}