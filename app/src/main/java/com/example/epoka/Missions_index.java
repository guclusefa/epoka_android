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

public class Missions_index extends Activity {

    MethodesEpoka mEpoka = new MethodesEpoka();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.missions_index);

        // ficher journalise
        try {
            ficher();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // get id salarie
    public String getSalId() {
        Bundle extras = getIntent().getExtras();
        String sal_id = extras.getString("id");
        return sal_id;
    }

    public void ficher() throws JSONException {
        // les elems
        TextView tv_hello;
        tv_hello = findViewById(R.id.tv_hello);

        // url
        String urlServiceWeb = "ficher.php?id="+getSalId();

        // si connecte (ddata= json)
        if (mEpoka.isJSONValid(mEpoka.getServerdataJSON(urlServiceWeb))) {
            // recupere data journaliste
            JSONArray array = new JSONArray(mEpoka.getServerdataJSON(urlServiceWeb));
            JSONObject object = array.getJSONObject(0);

            tv_hello.setText("Journaliste : "+object.getString("sal_prenom") + " " + object.getString("sal_nom"));
        } else {
            // finir si pas co (normalement pas possible mais au cas ou)
            finish();
        }
    }

    public void ajouter(View v) {
        // on fait passer id sal
        Intent intent = new Intent(getApplicationContext(), Missions_ajouter.class);
        intent.putExtra("id", getSalId());
        startActivity(intent);
    }

    public void quitter(View v) {
        finish();
    }
}