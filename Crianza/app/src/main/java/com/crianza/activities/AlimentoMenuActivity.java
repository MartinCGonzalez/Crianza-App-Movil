package com.crianza.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.crianza.R;

public class AlimentoMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_alimento);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    public void ingresoAlimento(View view) {
        Intent intent = new Intent(this, IngresoAlimentoActivity.class);
        startActivity(intent);
    }

    public void agregarAlimento(View view) {
        Intent intent = new Intent(this, AgregarAlimentoActivity.class);
        startActivity(intent);
    }

    public void modificarAlimento(View view) {
        Intent intent = new Intent(this, ModificarAlimentoActivity.class);
        startActivity(intent);
    }

    public void registroConsumoAlimento(View view) {
        Intent intent = new Intent(this, RegistroConsumoAlimentoActivity.class);
        startActivity(intent);
    }

    public void historicoConsumoAlimento(View view) {
        Intent intent = new Intent(this, HistoricoConsumoAlimentoActivity.class);
        startActivity(intent);
    }

}