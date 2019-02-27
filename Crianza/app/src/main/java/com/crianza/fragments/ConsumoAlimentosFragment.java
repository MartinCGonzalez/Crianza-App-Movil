package com.crianza.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.crianza.R;
import com.crianza.adapters.ConsumoAlimentosAdapter;
import com.crianza.dtos.ConsumoAlimentoDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsumoAlimentosFragment extends Fragment {

    ListView consumoAlimentoList;
    ArrayAdapter<ConsumoAlimentoDTO> consumoAlimentoAdapter;

    public ConsumoAlimentosFragment(){
    }

    public static ConsumoAlimentosFragment newInstance() {
        ConsumoAlimentosFragment fragment = new ConsumoAlimentosFragment();
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
        final View root = inflater.inflate(R.layout.fragment_consumo_alimento, container, false);

        // Instancia del ListView.
        consumoAlimentoList = (ListView) root.findViewById(R.id.consumoAlimento_list);

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
                String urlServicio = "http://192.168.1.2:8081/PFT-Crianza/rest/consumoAlimento/consumoAlimentosTodo";
                url = new URL(urlServicio);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("content-type", "application/json;charset = utf-8");

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                JSONArray respJSON = new JSONArray(getResponseText(in));

                //Se crea un listado de ConsumoAlimentoDTO
                List<ConsumoAlimentoDTO> consumoAlimentosListado = new ArrayList<>();

                //Se recorre el Json y se obtienen los datos a agregar al listado
                for (int i = 0; i < respJSON.length(); i++) {
                    JSONObject obj = respJSON.getJSONObject(i);
                    Long idT = obj.getLong("idTernero");
                    Long nroCaravana = obj.getLong("nroCaravana");
                    String fec = obj.getString("fecha");
                    String nombre = obj.getString("alimento");
                    BigDecimal cantidad = new BigDecimal(obj.getString("cantidad"));
                    String unidad = obj.getString("unidad");


                     //Se instancia una ConsumoAlimentoDTO con los datos obtenidos
                    ConsumoAlimentoDTO consumoAlimento = new ConsumoAlimentoDTO(idT, cantidad, fec, nroCaravana, nombre, unidad);

                    // se agrega al listado
                    consumoAlimentosListado.add(consumoAlimento);

                }

                consumoAlimentoAdapter = new ConsumoAlimentosAdapter(getActivity(), consumoAlimentosListado);

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
                consumoAlimentoList.setAdapter(consumoAlimentoAdapter);

            }
        }

        private String getResponseText(InputStream inStream) {
            // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
            return new Scanner(inStream).useDelimiter("\\A").next();
        }
    }

}