package com.crianza.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.crianza.R;
import com.crianza.adapters.TernerosAdapter;
import com.crianza.dtos.TerneroDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TerneroHistoricoConsumoAlimentoFragment extends Fragment {

    ListView ternerosList;
    ArrayAdapter<TerneroDTO> ternerosAdapter;
    EditText idA;

    public TerneroHistoricoConsumoAlimentoFragment(){
    }

    public static TerneroHistoricoConsumoAlimentoFragment newInstance() {
        TerneroHistoricoConsumoAlimentoFragment fragment = new TerneroHistoricoConsumoAlimentoFragment();
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
        final View root = inflater.inflate(R.layout.fragment_terneros_consumo_alimento, container, false);

        // Instancia del ListView.
        ternerosList = (ListView) root.findViewById(R.id.terneros_list);

        ObtenerAlimentosTask tarea = new ObtenerAlimentosTask();
        tarea.execute();
        //Se convierte el listado en clickleable

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
                String urlServicio = "http://192.168.1.2:8081/PFT-Crianza/rest/ternero/ternerosRegistroConsumo";
                url = new URL(urlServicio);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("content-type", "application/json;charset = utf-8");

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                JSONArray respJSON = new JSONArray(getResponseText(in));

                //Se crea un listado de AlimentoDTO
                List<TerneroDTO> ternerosListado = new ArrayList<>();

                //Se recorre el Json y se obtienen los datos a agregar al listado
                for (int i = 0; i < respJSON.length(); i++) {
                    JSONObject obj = respJSON.getJSONObject(i);
                    Long id = obj.getLong("idTernero");
                    Long nroCaravana = obj.getLong("nroCaravana");
                    Long idGuachera = obj.getLong("idGuachera");

                    //Se instancia una SucursalDTO con los datos obtenidos
                    TerneroDTO ternero = new TerneroDTO(id, nroCaravana, idGuachera);

                    // se agrega al listado
                    ternerosListado.add(ternero);

                }

                ternerosAdapter = new TernerosAdapter(getActivity(), ternerosListado);

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
                ternerosList.setAdapter(ternerosAdapter);

            }
        }

        private String getResponseText(InputStream inStream) {
            // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
            return new Scanner(inStream).useDelimiter("\\A").next();
        }
    }

}
