package com.example.fidelitycontrol;

import android.os.AsyncTask;
import android.util.Log;

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

public class ExtractOferteJSON extends AsyncTask<URL,Void,String> {
    public static List<Oferta> listaOferte=new ArrayList<Oferta>();
    JSONArray oferte=null;
    @Override
    protected String doInBackground(URL... urls) {
        HttpURLConnection conexiune = null;
        try {
            conexiune = (HttpURLConnection) urls[0].openConnection();
            conexiune.setRequestMethod("GET");
            InputStream inputStream = conexiune.getInputStream();

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String linie = "";
            String sbuf = "";
            while ((linie = bufferedReader.readLine()) != null) {
                sbuf += linie + "\n";
            }

            iaDateDinJSON(sbuf);

            return sbuf;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    public void iaDateDinJSON(String jsonStr){
        if(jsonStr!=null){
            JSONObject jsonObject=null;
            try {
                jsonObject=new JSONObject(jsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                oferte=jsonObject.getJSONArray("oferte");
                for(int i=0; i<oferte.length(); i++){
                    JSONObject o = oferte.getJSONObject(i);
                    String denumireOferta =o.getString("denumireOferta");
                    String mesajOferta = o.getString("mesajOferta");
                    int nrDeZileValabilitate=Integer.parseInt(o.getString("nrDeZileValabilitate"));
                    Oferta oferta=new Oferta(denumireOferta,mesajOferta,nrDeZileValabilitate);
                    listaOferte.add(oferta);
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
