package com.crianza.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.crianza.R;

public class EventoClinicoMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_evento_clinico);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void ingresoEventoClinico(View view) {
        Intent intent = new Intent(this, IngresoEventoClinicoActivity.class);
        startActivity(intent);
    }

    public void historicoEventoClinico(View view) {
        Intent intent = new Intent(this, HistoricoEventoClinicoActivity.class);
        startActivity(intent);
    }
}
