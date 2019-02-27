package com.crianza.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.crianza.R;
import com.crianza.fragments.EventoClinicoHistoricoFragment;

import static com.crianza.utils.ConnectivityHelper.isConnected;

public class HistoricoEventoClinicoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_evento_clinico);

        //Se llama al administrador de fragmentos y mediante el método add() se agrega la instancia de AlimentosFragment.
        EventoClinicoHistoricoFragment eventoClinicoHistoricoFragment = (EventoClinicoHistoricoFragment)
                getSupportFragmentManager().findFragmentById(R.id.historicoEventoClinico_container);

        if (eventoClinicoHistoricoFragment == null) {
            eventoClinicoHistoricoFragment = eventoClinicoHistoricoFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.historicoEventoClinico_container, eventoClinicoHistoricoFragment)
                    .commit();
        }
        //Check de conexión a internet
        if (isConnected(this)) {
//            Toast.makeText(AgregarAlimentoActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(HistoricoEventoClinicoActivity.this, "Sin Conexión a Internet", Toast.LENGTH_SHORT).show();
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
