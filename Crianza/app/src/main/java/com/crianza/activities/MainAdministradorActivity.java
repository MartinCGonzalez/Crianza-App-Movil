package com.crianza.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.crianza.R;

public class MainAdministradorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_administrador);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    //Menu
    public void menuAlimento(View view) {
        Intent intent = new Intent(this, AlimentoMenuActivity.class);
        startActivity(intent);
    }

    public void menuPeso(View view) {
        Intent intent = new Intent(this, PesoMenuActivity.class);
        startActivity(intent);
    }

    public void menuEventoClinico(View view) {
        Intent intent = new Intent(this, EventoClinicoMenuActivity.class);
        startActivity(intent);
    }

    public void menuTernero(View view) {
        Intent intent = new Intent(this, TerneroMenuActivity.class);
        startActivity(intent);
    }

    public void menuUsuario(View view) {
        Intent intent = new Intent(this, UsuarioMenuActivity.class);
        startActivity(intent);
    }


}
