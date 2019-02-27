package com.crianza.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.crianza.R;

public class TerneroMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ternero);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void ingresoTernero(View view) {
        Intent intent = new Intent(this, IngresoTerneroActivity.class);
        startActivity(intent);
    }

    public void listadoTernero(View view) {
        Intent intent = new Intent(this, ListadoTerneroActivity.class);
        startActivity(intent);
    }
}
