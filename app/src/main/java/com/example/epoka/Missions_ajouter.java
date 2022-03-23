package com.example.epoka;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Missions_ajouter extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.missions_ajouter);
    }

    // get id salarie
    public String getSalId() {
        Bundle extras = getIntent().getExtras();
        String sal_id = extras.getString("id");
        return sal_id;
    }

    public void ajouter(View v) throws JSONException {
        // les params
        EditText et_debut, et_fin;
        Spinner spinner_lieu;
        TextView tv_erreur_ajouter_mission;

        et_debut = (EditText) findViewById(R.id.et_debut);
        et_fin = (EditText) findViewById(R.id.et_fin);
        spinner_lieu = findViewById(R.id.spinner_lieu);
        tv_erreur_ajouter_mission = findViewById(R.id.tv_erreur_ajouter_mission);

        MethodesEpoka mEpoka = new MethodesEpoka();
        String urlServiceWeb = "ajouter.php?id="+getSalId()+"&lieu="+spinner_lieu.getSelectedItemPosition() + 1+"&debut="+et_debut.getText()+"&fin="+et_fin.getText();

        if (mEpoka.getServerdataJSON(urlServiceWeb).equals("1")) {
            tv_erreur_ajouter_mission.setText("Mission ajouté avec succès");
            Toast.makeText(this, "Mission ajouté avec succès", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            tv_erreur_ajouter_mission.setText(mEpoka.getServerdataJSON(urlServiceWeb));
        }
    }

    public void quitter(View v) {
        finish();
    }
}