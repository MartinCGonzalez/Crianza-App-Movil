package com.crianza.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.crianza.R;
import com.crianza.dtos.AlimentoDTO;
import com.crianza.validaciones.ValidacionDatos;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.crianza.utils.ConnectivityHelper.isConnected;

public class AgregarAlimentoActivity extends AppCompatActivity {

    List<String> alimentosList = new ArrayList<String>();
    String alimento;
    Spinner sp_alimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_alimento);

        //Se ejecuta el método que obtiene el json y pobla de datos el spinner
        ObtenerAlimentosTask tarea = new ObtenerAlimentosTask();
        tarea.execute();

        //Check de conexión a internet
        if (isConnected(this)) {
//            Toast.makeText(AgregarAlimentoActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(AgregarAlimentoActivity.this, "Sin Conexión a Internet", Toast.LENGTH_SHORT).show();
            isConnected(this);

        }
    }

    //Check de conexión a internet
    public void onResume() {
        super.onResume();
        isConnected(this);
    }

    public void onPause() {
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


    public void agregarAlimento(View view) {

        EditText txtCantidad = (EditText) findViewById(R.id.txtCantidad);

        String cantidad = txtCantidad.getText().toString();

        String nombre = sp_alimento.getSelectedItem().toString();

        if (!cantidad.isEmpty()) {

            BigDecimal cantidadAli = BigDecimal.valueOf(Double.valueOf(cantidad));

            if (ValidacionDatos.validarAgregar(cantidadAli)) {

                AlimentoDTO unAlimentoDTO = new AlimentoDTO(cantidadAli, nombre);

                PutAlimento postAlimento = new PutAlimento(unAlimentoDTO);
                postAlimento.execute();
            }
        } else {
            Toast.makeText(AgregarAlimentoActivity.this, "Olvidaste ingresar la cantidad", Toast.LENGTH_LONG).show();

        }

    }

    //Metodo para agregar alimento
    private class PutAlimento extends AsyncTask<Void, Void, Boolean> {
        boolean result = true;

        private AlimentoDTO unAlimento;

        private PutAlimento(AlimentoDTO alimentoDTO) {

            unAlimento = alimentoDTO;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {

                AlimentoDTO unAlimentoDTO = new AlimentoDTO();
                unAlimentoDTO = unAlimento;

                String request = "http://192.168.1.2:8081/PFT-Crianza/rest/alimento/agregarAlimento";

                final URL url = new URL(request);
                final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setInstanceFollowRedirects(false);
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Accept", "application/json;charset=utf-8");
                String type = "application/json;charset = utf-8";
                connection.setRequestProperty("Content-Type", type);

                final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                outputStreamWriter.write(gson.toJson(unAlimentoDTO));
                outputStreamWriter.flush();

                int status = connection.getResponseCode();


                //Se valida de que la solicitud fue exitosa
                if (status == 200 || status == 201) {

                    return true;

                } else {
                    return false;
                }

            } catch (Exception ex) {
                Log.e("ServicioRest", "Error!", ex);
                result = false;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean == true) {
                Toast.makeText(AgregarAlimentoActivity.this, "Alimento Agregado", Toast.LENGTH_SHORT).show();

            } else {
//                    Toast.makeText(AgregarAlimentoActivity.this, "Alimento No Agregado", Toast.LENGTH_LONG).show();
                Toast.makeText(AgregarAlimentoActivity.this, "Error al conectar con servidor, Por favor contacte a su administrador", Toast.LENGTH_LONG).show();


            }
        }
    }

    //Método para consumir servicio Rest y obtener los alimentos para mostrar en el spinner
    private class ObtenerAlimentosTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean result = true;
            URL url;
            HttpURLConnection urlConnection = null;
            try {

                //Se especifica la url para consumir el servicio Rest
                String urlServicio = "http://192.168.1.2:8081/PFT-Crianza/rest/alimento/alimentos";
                url = new URL(urlServicio);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("content-type", "application/json");

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                JSONArray respJSON = new JSONArray(getResponseText(in));

                //Se crea un listado de AlimentoDTO
                List<AlimentoDTO> alimentos = new ArrayList<>();
                Bundle b = new Bundle();
                //Se recorre el Json y se obtienen los datos a agregar al listado
                for (int i = 0; i < respJSON.length(); i++) {
                    JSONObject obj = respJSON.getJSONObject(i);
                    String nombre = obj.getString("nombre");

                    //Se instancia un AlimentoDTO con los datos obtenidos
                    AlimentoDTO alimentoDTO = new AlimentoDTO(nombre);

                    //Se agrega a la lista de alimentos
                    alimentos.add(alimentoDTO);


                    //Se carga el listado con los nombres del alimento para mostrar en el spinner
                    alimentosList.add(obj.getString("nombre"));
                }


            } catch (Exception ex) {
                Log.e("ServicioRest", "Error!", ex);
                result = false;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean == true) {

                // Se localiza el spinner
                sp_alimento = (Spinner) findViewById(R.id.sp_alimento);

                // Se setea el spinner al adaptador
                sp_alimento.setAdapter(new ArrayAdapter<String>(AgregarAlimentoActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        alimentosList));

                //Se setea el método que perimte seleccionar un elemento y guardar en una variable el valor seleccionado
                sp_alimento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        alimento = sp_alimento.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        //nothing
                    }
                });

            }
        }

        private String getResponseText(InputStream inStream) {
            // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
            return new Scanner(inStream).useDelimiter("\\A").next();
        }
    }
}


