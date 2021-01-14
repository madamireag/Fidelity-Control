package com.example.fidelitycontrol;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExtractCardsJSON extends AsyncTask<URL,Void,String> {
public static List<FidelityCard> listaCarduri=new ArrayList<FidelityCard>();
public static String DataExtragere;
public static String Titlu;
JSONArray cards=null;
    @Override
    protected String doInBackground(URL... urls) {
        HttpURLConnection conexiune = null;
        try {
            conexiune = (HttpURLConnection) urls[0].openConnection();
            conexiune.setRequestMethod("GET");
            InputStream ist = conexiune.getInputStream();

            InputStreamReader isr = new InputStreamReader(ist);
            BufferedReader br = new BufferedReader(isr);
            String linie = "";
            String buffer = "";
            while ((linie = br.readLine()) != null) {
                buffer += linie + "\n";
            }

            iaDateDinJSON(buffer);

            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void iaDateDinJSON(String jsonString){
        if(jsonString!=null){
            JSONObject jsonObject=null;
            try {
                jsonObject=new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONObject dateJson= jsonObject.getJSONObject("date");
                Titlu = dateJson.getString("titlu");
                DataExtragere = dateJson.getString("data");
                cards=dateJson.getJSONArray("carduri");

                for(int i=0; i<cards.length(); i++){
                    JSONObject c = cards.getJSONObject(i);
                    String number =c.getString("CardNO");
                    String company = c.getString("Companie");
                    Locale locale = new Locale("ro", "RO");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy",locale);
                    Date data= null;
                    try {
                        data = sdf.parse(c.getString("Data"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String owner=c.getString("Proprietar");
                    int nrPuncte=Integer.parseInt(c.getString("NrPuncte"));
                    FidelityCard card=new FidelityCard(number,nrPuncte,company,data,owner);
                    listaCarduri.add(card);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else
        {
            Log.e("iaDateDinJSON", String.valueOf(R.string.eroare_json));
        }
    }


}
