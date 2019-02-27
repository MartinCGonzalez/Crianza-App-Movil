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
import com.crianza.adapters.AlimentosAdapter;
import com.crianza.adapters.PesoAdapter;
import com.crianza.dtos.AlimentoDTO;
import com.crianza.dtos.PesoDTO;

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

public class PesoHistoricoFragment extends Fragment {

    ListView pesoList;
    ArrayAdapter<PesoDTO> pesoAdapter;

    public PesoHistoricoFragment(){
    }

    public static PesoHistoricoFragment newInstance() {
        PesoHistoricoFragment fragment = new PesoHistoricoFragment();
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
        final View root = inflater.inflate(R.layout.fragment_historico_peso, container, false);

        // Instancia del ListView.
        pesoList = (ListView) root.findViewById(R.id.pesos_list);

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
                String urlServicio = "http://192.168.1.2:8081/PFT-Crianza/rest/peso/pesoTodo";
                url = new URL(urlServicio);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("content-type", "application/json;charset = utf-8");

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                JSONArray respJSON = new JSONArray(getResponseText(in));

                //Se crea un listado de PesoDTO
                List<PesoDTO> pesosListado = new ArrayList<>();

                //Se recorre el Json y se obtienen los datos a agregar al listado
                for (int i = 0; i < respJSON.length(); i++) {
                    JSONObject obj = respJSON.getJSONObject(i);
                    Long id = obj.getLong("idTernero");
                    String fecha = obj.getString("fecha");
                    BigDecimal peso = new BigDecimal(obj.getString("peso"));
                    BigDecimal ganancia = new BigDecimal(obj.getString("ganancia"));
                    String tipoRegistro = obj.getString("tipoRegistro");

                    //Se instancia una SucursalDTO con los datos obtenidos
                    PesoDTO unPeso = new PesoDTO(id, fecha, peso, ganancia, tipoRegistro);

                    // se agrega al listado
                    pesosListado.add(unPeso);

                }

                pesoAdapter = new PesoAdapter(getActivity(), pesosListado);

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
                pesoList.setAdapter(pesoAdapter);

            }
        }

        private String getResponseText(InputStream inStream) {
            // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
            return new Scanner(inStream).useDelimiter("\\A").next();
        }
    }

}