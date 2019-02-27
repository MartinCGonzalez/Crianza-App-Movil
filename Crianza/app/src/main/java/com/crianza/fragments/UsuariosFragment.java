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
import com.crianza.adapters.UsuarioAdapter;
import com.crianza.dtos.UsuarioDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UsuariosFragment extends Fragment {

    ListView usuariosList;
    ArrayAdapter<UsuarioDTO> usuariosAdapter;

    public UsuariosFragment(){
    }

    public static UsuariosFragment newInstance() {
        UsuariosFragment fragment = new UsuariosFragment();
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
        final View root = inflater.inflate(R.layout.fragment_usuarios, container, false);

        // Instancia del ListView.
        usuariosList = (ListView) root.findViewById(R.id.usuarios_list);

        ObtenerUsuariosTask tarea = new ObtenerUsuariosTask();
        tarea.execute();
        //Se convierte el listado en clickleable
        usuariosList.setClickable(true);

        //Metodo que obtiene el alimento seleccionado del listado y carga los datos en los EditText
        usuariosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                //Se obtiene el objeto de la posicion seleccionada
                Object objUsuario = usuariosList.getItemAtPosition(position);

                //Parseo el objeto obtenido a un objeto de tipo AlimentoDTO
                UsuarioDTO usuario = (UsuarioDTO) objUsuario;

                String idUsuario = usuario.getIdUsuario().toString();

                EditText idU = (EditText)  getActivity().findViewById(R.id.txtIdUsuario);
                idU.setText(idUsuario,TextView.BufferType.EDITABLE);
            }
        });

        return  root;
    }
    private class ObtenerUsuariosTask extends AsyncTask<Void,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean result = true;
            URL url;
            HttpURLConnection urlConnection = null;
            try {

                //Se especifica la url para consumir el servicio Rest
                String urlServicio = "http://192.168.1.2:8081/PFT-Crianza/rest/usuario/usuariosTodo";
                url = new URL(urlServicio);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("content-type", "application/json;charset = utf-8");

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                JSONArray respJSON = new JSONArray(getResponseText(in));

                //Se crea un listado de UsuarioDTO
                List<UsuarioDTO> usuarioListado = new ArrayList<>();

                //Se recorre el Json y se obtienen los datos a agregar al listado
                for (int i = 0; i < respJSON.length(); i++) {
                    JSONObject obj = respJSON.getJSONObject(i);
                    Long id = obj.getLong("idUsuario");
                    String nombre = obj.getString("nombre");
                    String apellido = obj.getString("apellido");
                    String perfil = obj.getString("perfil");
                    String usr = obj.getString("usuario");
                    String contraseña = obj.getString("contraseña");

                    //Se instancia una UsuarioDTO con los datos obtenidos
                    UsuarioDTO usuario = new UsuarioDTO(id, nombre, apellido, perfil, usr, contraseña);

                    // se agrega al listado
                    usuarioListado.add(usuario);

                }
                usuariosAdapter = new UsuarioAdapter(getActivity(), usuarioListado);

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
                usuariosList.setAdapter(usuariosAdapter);

            }
        }

        private String getResponseText(InputStream inStream) {
            // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
            return new Scanner(inStream).useDelimiter("\\A").next();
        }
    }

}
