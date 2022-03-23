package com.example.epoka;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MethodesEpoka {
    public String getURL(){
        String url = "http://192.168.1.24/epoka_service_web/";
        return url;
    }

    public String getServerdataJSON(String uriString) {
        InputStream is = null;
        String data = "";

        // Autoriser les opérations réseau sur le thread principal
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            URL url = new URL(getURL() + uriString);
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
}
