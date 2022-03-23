package com.example.epoka;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Locale;

public class connexion extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);
    }

    private String getServerdataJSON(String uriString) {
        InputStream is = null;
        String data = "";

        // Autoriser les opérations réseau sur le thread principal
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            URL url = new URL(uriString);
            HttpURLConnection connexion = (HttpURLConnection) url.openConnection();
            connexion.connect();
            is = connexion.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            data = br.readLine();
        } catch (Exception expt) {
            Log.e("log_tag", "Erreur pendant la récupération des données : " + expt.toString());
        };
        return (data);
    }

    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public void connexion(View v) throws JSONException {
        // les variables
        EditText et_id, et_mdp;
        TextView tv_erreur_connexion;
        String urlServiceWeb;

        // les elements
        et_id = (EditText) findViewById(R.id.et_id);
        et_mdp = (EditText) findViewById(R.id.et_mdp);
        tv_erreur_connexion = findViewById(R.id.tv_erreur_connexion);

        // url req
        urlServiceWeb = "http://172.16.75.32/epoka/connexion.php?id="+et_id.getText()+"&mdp="+et_mdp.getText();

        //check si string peut etre un json
        if (isJSONValid(getServerdataJSON(urlServiceWeb))) {
            JSONArray array = new JSONArray(getServerdataJSON(urlServiceWeb));
            JSONObject object = array.getJSONObject(0);
            tv_erreur_connexion.setText(object.getString("sal_id"));
        } else {
            tv_erreur_connexion.setText("pas un json");
        }

        // Intent intent = new Intent(getApplicationContext(), missions_index.class);
        // startActivity(intent);
    }
}