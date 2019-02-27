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
import com.crianza.dtos.AlimentoDTO;
import com.crianza.dtos.UnidadDTO;
import com.google.gson.Gson;

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

public class AlimentoConsumoAlimentoFragment extends Fragment {

    ListView alimentosList;
    ArrayAdapter<AlimentoDTO> alimentosAdapter;
    EditText idA;

    public AlimentoConsumoAlimentoFragment(){
    }

    public static AlimentoConsumoAlimentoFragment newInstance() {
        AlimentoConsumoAlimentoFragment fragment = new AlimentoConsumoAlimentoFragment();
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
        final View root = inflater.inflate(R.layout.fragment_alimentos, container, false);

        // Instancia del ListView.
        alimentosList = (ListView) root.findViewById(R.id.alimentos_list);

        ObtenerAlimentosTask tarea = new ObtenerAlimentosTask();
        tarea.execute();
        //Se convierte el listado en clickleable
        alimentosList.setClickable(true);

        //Metodo que obtiene el alimento seleccionado del listado y carga los datos en los EditText
        alimentosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                //Se obtiene el objeto de la posicion seleccionada
                Object objAlimento = alimentosList.getItemAtPosition(position);

                //Parseo el objeto obtenido a un objeto de tipo AlimentoDTO
                AlimentoDTO alimento = (AlimentoDTO) objAlimento;

                String idAlimento = alimento.getIdAlimento().toString();
                String stockAlimento = alimento.getCantidad().toString();

                EditText idA = (EditText)  getActivity().findViewById(R.id.txtIdAlimento);
                idA.setText(idAlimento,TextView.BufferType.EDITABLE);

                EditText stock = (EditText)  getActivity().findViewById(R.id.txtStock);
                stock.setText(stockAlimento,TextView.BufferType.EDITABLE);



            }
        });

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
                String urlServicio = "http://192.168.1.2:8081/PFT-Crianza/rest/alimento/alimentosTodo";
                url = new URL(urlServicio);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("content-type", "application/json;charset = utf-8");

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                JSONArray respJSON = new JSONArray(getResponseText(in));

                //Se crea un listado de AlimentoDTO
                List<AlimentoDTO> alimentosListado = new ArrayList<>();

                //Se recorre el Json y se obtienen los datos a agregar al listado
                for (int i = 0; i < respJSON.length(); i++) {
                    JSONObject obj = respJSON.getJSONObject(i);
                    Long id = obj.getLong("idAlimento");
                    String nombre = obj.getString("nombre");
                    BigDecimal costoUnidad = new BigDecimal(obj.getString("costoUnidad"));
                    BigDecimal cantidad = new BigDecimal(obj.getString("cantidad"));
                    String unidad = obj.getString("tipoUnidad");

                    //Se instancia una SucursalDTO con los datos obtenidos
                    AlimentoDTO sucursal = new AlimentoDTO(id, nombre, cantidad, costoUnidad, unidad);

                    // se agrega al listado
                    alimentosListado.add(sucursal);

                }

                alimentosAdapter = new AlimentosAdapter(getActivity(), alimentosListado);

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
                alimentosList.setAdapter(alimentosAdapter);

            }
        }

        private String getResponseText(InputStream inStream) {
            // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
            return new Scanner(inStream).useDelimiter("\\A").next();
        }
    }

}
