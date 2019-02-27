package com.crianza.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.crianza.R;

public class PesoMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_peso);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void ingresoGananciaPeso(View view) {
        Intent intent = new Intent(this, IngresoGananciaPesoActivity.class);
        startActivity(intent);
    }

    public void historicoGananciaPeso(View view) {
        Intent intent = new Intent(this, HistoricoPesoActivity.class);
        startActivity(intent);
    }

}