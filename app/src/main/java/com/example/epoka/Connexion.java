package com.example.epoka;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Connexion extends Activity {

    MethodesEpoka mEpoka = new MethodesEpoka();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);
    }

    public void connexion(View v) throws JSONException {
        // les variables
        EditText et_id, et_mdp;
        TextView tv_erreur_connexion;

        // les elements
        et_id = (EditText) findViewById(R.id.et_id);
        et_mdp = (EditText) findViewById(R.id.et_mdp);
        tv_erreur_connexion = findViewById(R.id.tv_erreur_connexion);

        // url
        String urlServiceWeb = "connexion.php?id="+et_id.getText()+"&mdp="+et_mdp.getText();

        // si connecte (ddata= json)
        if (mEpoka.isJSONValid(mEpoka.getServerdataJSON(urlServiceWeb))) {
            // recup des data journaliste
            JSONArray array = new JSONArray(mEpoka.getServerdataJSON(urlServiceWeb));
            JSONObject object = array.getJSONObject(0);

            // les msgs valide
            tv_erreur_connexion.setText("Connexion avec succès !");
            Toast.makeText(this, "Bonjour "+object.getString("sal_prenom") + " " + object.getString("sal_nom"), Toast.LENGTH_SHORT).show();

            // direction + extra id
            Intent intent = new Intent(getApplicationContext(), Missions_index.class);
            intent.putExtra("id", object.getString("sal_id"));
            startActivity(intent);
        } else {
            // msg d'erreur si pas du json
            tv_erreur_connexion.setText(mEpoka.getServerdataJSON(urlServiceWeb));
        }
    }
}