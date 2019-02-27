package com.crianza.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.crianza.R;

public class UsuarioMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuario);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void ingresoUsuario(View view) {
        Intent intent = new Intent(this, IngresoUsuarioActivity.class);
        startActivity(intent);
    }

    public void modificarUsuario(View view) {
        Intent intent = new Intent(this, ModificarUsuarioActivity.class);
        startActivity(intent);
    }

    public void bajaUsuario(View view) {
        Intent intent = new Intent(this, BajaUsuarioActivity.class);
        startActivity(intent);
    }
}
