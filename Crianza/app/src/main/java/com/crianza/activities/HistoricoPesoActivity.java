package com.crianza.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.crianza.R;
import com.crianza.fragments.AlimentosFragment;
import com.crianza.fragments.PesoHistoricoFragment;

import java.util.ArrayList;
import java.util.List;

import static com.crianza.utils.ConnectivityHelper.isConnected;


public class HistoricoPesoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_peso);

        //Se llama al administrador de fragmentos y mediante el método add() se agrega la instancia de PesoHistoricoFragment.
        PesoHistoricoFragment pesoHistoricoFragment = (PesoHistoricoFragment)
                getSupportFragmentManager().findFragmentById(R.id.historicoPeso_container);

        if (pesoHistoricoFragment == null) {
            pesoHistoricoFragment = pesoHistoricoFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.historicoPeso_container, pesoHistoricoFragment)
                    .commit();
        }

        //Check de conexión a internet
        if (isConnected(this)) {
//            Toast.makeText(AgregarAlimentoActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(HistoricoPesoActivity.this, "Sin Conexión a Internet", Toast.LENGTH_SHORT).show();
            isConnected(this);

        }
    }

    //Check de conexión a internet
    public void onResume() {
        super.onResume();
        isConnected(this);
    }

    public void onPause(){
        super.onPause();
        isConnected(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (!hasFocus) {
            //barra abajo
        } else {
            //barra arriba
            isConnected(this);
        }
        super.onWindowFocusChanged(hasFocus);

    }
}
