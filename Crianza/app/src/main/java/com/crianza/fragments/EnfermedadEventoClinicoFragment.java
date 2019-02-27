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
import com.crianza.adapters.EnfermedadAdapter;
import com.crianza.dtos.EnfermedadDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EnfermedadEventoClinicoFragment extends Fragment {

    ListView enfermedadesList;
    ArrayAdapter<EnfermedadDTO> enfermedadesAdapter;

    public EnfermedadEventoClinicoFragment(){
    }

    public static EnfermedadEventoClinicoFragment newInstance() {
        EnfermedadEventoClinicoFragment fragment = new EnfermedadEventoClinicoFragment();
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
        final View root = inflater.inflate(R.layout.fragment_enfermedad_evento_clinico, container, false);

        // Instancia del ListView.
        enfermedadesList = (ListView) root.findViewById(R.id.enfermedades_list);

        ObtenerAlimentosTask tarea = new ObtenerAlimentosTask();
        tarea.execute();

        //Se convierte el listado en clickleable
        enfermedadesList.setClickable(true);

        //Metodo que obtiene el alimento seleccionado del listado y carga los datos en los EditText
        enfermedadesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                //Se obtiene el objeto de la posicion seleccionada
                Object objEnfermedad = enfermedadesList.getItemAtPosition(position);

                //Parseo el objeto obtenido a un objeto de tipo EnfermedadDTO
                EnfermedadDTO enfermedad = (EnfermedadDTO) objEnfermedad;

                String idEnfermedad = enfermedad.getIdEnfermedad().toString();

                EditText idE = (EditText)  getActivity().findViewById(R.id.txtIdEnfermedad);
                idE.setText(idEnfermedad,TextView.BufferType.EDITABLE);

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
                String urlServicio = "http://192.168.1.2:8081/PFT-Crianza/rest/eventoClinico/enfermedades";
                url = new URL(urlServicio);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("content-type", "application/json;charset = utf-8");

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                JSONArray respJSON = new JSONArray(getResponseText(in));

                //Se crea un listado de EnfermedadDTO
                List<EnfermedadDTO> enfermedadesListado = new ArrayList<>();

                //Se recorre el Json y se obtienen los datos a agregar al listado
                for (int i = 0; i < respJSON.length(); i++) {
                    JSONObject obj = respJSON.getJSONObject(i);
                    Long id = obj.getLong("idEnfermedad");
                    String nombre = obj.getString("nombre");
                    Long gravedad = obj.getLong("gradoGravedad");

                    //Se instancia una EnfermedadDTO con los datos obtenidos
                    EnfermedadDTO enfermedad = new EnfermedadDTO(id, nombre, gravedad);

                    // se agrega al listado
                    enfermedadesListado.add(enfermedad);

                }

                enfermedadesAdapter = new EnfermedadAdapter(getActivity(), enfermedadesListado);

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
                enfermedadesList.setAdapter(enfermedadesAdapter);

            }
        }

        private String getResponseText(InputStream inStream) {
            // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
            return new Scanner(inStream).useDelimiter("\\A").next();
        }
    }

}
