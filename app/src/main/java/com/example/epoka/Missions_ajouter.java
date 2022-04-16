package com.example.epoka;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Missions_ajouter extends Activity {

    MethodesEpoka mEpoka = new MethodesEpoka();
    // spinner
    SearchableSpinner spinner_lieu;

    // les dates
    private DatePickerDialog datePickerDialog, datePickerDialog2;
    private Button btn_debut, btn_fin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.missions_ajouter);

        // spinner
        spinner_lieu = findViewById(R.id.spinner_lieu);
        spinner_lieu.setTitle("Choisir une commune");
        spinner_lieu.setPositiveButton("Fermer");

        // array
        ArrayList<String> list = new ArrayList<>();
        try {
            String urlServiceWeb = "getCommunes.php";
            JSONArray array = new JSONArray(mEpoka.getServerdataJSON(urlServiceWeb));
            for (int i = 0; i < array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                list.add(object.getString("com_cp") + " - " + object.getString("com_nom"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // afficher la liste
        spinner_lieu.setAdapter(new ArrayAdapter<>(Missions_ajouter.this,
                android.R.layout.simple_spinner_dropdown_item,list));

        // initialisation datepicker + set date ajd
        initDatePicker();
        initDatePicker2();
        btn_debut = findViewById(R.id.btn_debut);
        btn_fin = findViewById(R.id.btn_fin);
        btn_debut.setText(mEpoka.getTodaysDate());
        btn_fin.setText(mEpoka.getTodaysDate());
    }

    // get id salarie
    public String getSalId() {
        Bundle extras = getIntent().getExtras();
        String sal_id = extras.getString("id");
        return sal_id;
    }

    // les datess
    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener =  new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month  = month+1;
                String date = mEpoka.makeDateString(day, month, year);
                btn_debut.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
    }

    private void initDatePicker2(){
        DatePickerDialog.OnDateSetListener dateSetListener =  new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month  = month+1;
                String date = mEpoka.makeDateString(day, month, year);
                btn_fin.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog2 = new DatePickerDialog(this, dateSetListener, year, month, day);
    }

    public void openDatePicker(View v){
        datePickerDialog.show();
    }

    public void openDatePicker2(View v){
        datePickerDialog2.show();
    }

    // les actions
    public void ajouter(View v) throws JSONException {
        // les params
        Button btn_debut, btn_fin;
        Spinner spinner_lieu;
        TextView tv_erreur_ajouter_mission;

        btn_debut = (Button) findViewById(R.id.btn_debut);
        btn_fin = (Button) findViewById(R.id.btn_fin);
        spinner_lieu = findViewById(R.id.spinner_lieu);
        tv_erreur_ajouter_mission = findViewById(R.id.tv_erreur_ajouter_mission);

        // recupere position (id) communes
        int lieu = spinner_lieu.getSelectedItemPosition() +1;

        String urlServiceWeb = "ajouter.php?id="+getSalId()+"&lieu="+lieu+"&debut="+btn_debut.getText() +"&fin="+btn_fin.getText();
        // si mission est ajouté
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