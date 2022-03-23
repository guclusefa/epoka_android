package com.example.epoka;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class missions_index extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.missions_index);
    }

    public void ajouter(View v) {
        Intent intent = new Intent(getApplicationContext(), missions_ajouter.class);
        startActivity(intent);
    }

    public void quitter(View v) {
        Intent intent = new Intent(getApplicationContext(), connexion.class);
        startActivity(intent);
    }
}