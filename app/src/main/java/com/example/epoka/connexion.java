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
        }
        ;
        return (data);
    }

    public void connexion(View v) {
        EditText et_id, et_mdp;
        TextView tv_erreur_connexion;
        String urlServiceWeb;

        et_id = (EditText) findViewById(R.id.et_id);
        et_mdp = (EditText) findViewById(R.id.et_mdp);
        tv_erreur_connexion = findViewById(R.id.tv_erreur_connexion);

        urlServiceWeb = "http://localhost/epoka/connexion.php?id=1&mdp=123";
        tv_erreur_connexion.setText(getServerdataJSON(urlServiceWeb));

        // Intent intent = new Intent(getApplicationContext(), missions_index.class);
        // startActivity(intent);
    }
}