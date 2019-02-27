package com.crianza.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.crianza.R;
import com.crianza.adapters.EnfermedadAdapter;
import com.crianza.adapters.EventoClinicoAdapter;
import com.crianza.dtos.EnfermedadDTO;
import com.crianza.dtos.EventoClinicoDTO;
import com.crianza.dtos.PesoDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EventoClinicoHistoricoFragment extends Fragment {

    ListView eventoClinicoList;
    ArrayAdapter<EventoClinicoDTO> eventoClinicoAdapter;

    public EventoClinicoHistoricoFragment(){
    }

    public static EventoClinicoHistoricoFragment newInstance() {
        EventoClinicoHistoricoFragment fragment = new EventoClinicoHistoricoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        final View root = inflater.inflate(R.layout.fragment_eventos_clinicos, container, false);

        // Instancia del ListView.
        eventoClinicoList = (ListView) root.findViewById(R.id.eventosClinicos_List);

        ObtenerAlimentosTask tarea = new ObtenerAlimentosTask();
        tarea.execute();

        return  root;

    }
    private class ObtenerAlimentosTask extends AsyncTask<Void,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean result = true;
            URL url;
            HttpURLConnection urlConnection = null;
            try {

                //Se especifica la url para consumir el servicio Rest
                String urlServicio = "http://192.168.1.2:8081/PFT-Crianza/rest/eventoClinico/eventosClinicos";
                url = new URL(urlServicio);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("content-type", "application/json;charset = utf-8");

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                JSONArray respJSON = new JSONArray(getResponseText(in));

                //Se crea un listado de EnfermedadDTO
                List<EventoClinicoDTO> eventosClinicosListado = new ArrayList<>();

                //Se recorre el Json y se obtienen los datos a agregar al listado
                for (int i = 0; i < respJSON.length(); i++) {
                    JSONObject obj = respJSON.getJSONObject(i);
                    Long idT = obj.getLong("idTernero");
                    String fechaNac = obj.getString("fechaNacimiento");
                    Long diasVida = obj.getLong("diasVida");
                    String fechaDesde = obj.getString("fechaDesde");
                    String fechaHasta = obj.getString("fechaHasta");
                    String nomEnfermedad = obj.getString("nombreEnfermedad");
                    Long gravedad = obj.getLong("gravedad");

                    //Se instancia una EventoClinicoDTO con los datos obtenidos
                    EventoClinicoDTO eventoClinico = new EventoClinicoDTO(idT,fechaNac,diasVida,fechaDesde,fechaHasta, nomEnfermedad, gravedad);

                    // se agrega al listado
                    eventosClinicosListado.add(eventoClinico);

                }

                eventoClinicoAdapter = new EventoClinicoAdapter(getActivity(), eventosClinicosListado);

            } catch (Exception ex) {
                Log.e("ServicioRest", "Error!", ex);
                result = false;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean == true) {
                //Relacionando la list view con el adaptador
                eventoClinicoList.setAdapter(eventoClinicoAdapter);

            }
        }

        private String getResponseText(InputStream inStream) {
            // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
            return new Scanner(inStream).useDelimiter("\\A").next();
        }
    }

}
