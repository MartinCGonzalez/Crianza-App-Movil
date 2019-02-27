package com.crianza.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.crianza.R;
import com.crianza.dtos.AlimentoDTO;


import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.crianza.dtos.PesoDTO;
import com.crianza.utils.GlobalApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.crianza.validaciones.ValidacionDatos;

import static com.crianza.utils.ConnectivityHelper.isConnected;


public class IngresoAlimentoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_alimento);

        //Check de conexión a internet
        if (isConnected(this)) {
//            Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(IngresoAlimentoActivity.this, "Sin Conexión a Internet", Toast.LENGTH_SHORT).show();
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

    public void help(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Alimentos Disponibles:\n" + "\n" +
                "CALOSTRO_NATURAL\n" +
                "CALOSTRO_FORZADO\n" +
                "LECHE\n" +
                "SUSTITUTO_LACTEO\n" +
                "INICIADOR\n" +
                "RACION\n")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

            AlertDialog alert = builder.create();
            alert.show();

    }

    public void ingresoAlimento(View view) throws ParseException {

        EditText txtNombre = (EditText) findViewById(R.id.txtNombre);
        EditText txtCosto = (EditText) findViewById(R.id.txtCostoUnidad);
        EditText txtCantidad = (EditText) findViewById(R.id.txtCantidad);

        //Cargo los campos en String para verificar que esten cargados
        String nombre = txtNombre.getText().toString().toUpperCase().trim();
        String costoUnidad = txtCosto.getText().toString();
        String cantidad = txtCantidad.getText().toString();

        //Verifico campos ingresados
        if(!nombre.isEmpty() && !costoUnidad.isEmpty() && !cantidad.isEmpty()){

            //Transformo los datos para el insert
            BigDecimal costo = BigDecimal.valueOf(Double.valueOf(costoUnidad));
            BigDecimal cant = BigDecimal.valueOf(Double.valueOf(cantidad));

            try {
                //Validaciones
                if (ValidacionDatos.validarAlimento(costo,cant)) {

                    //Carga de datos al DTO
                    AlimentoDTO alimentoDTO = new AlimentoDTO(nombre, costo, cant);

                    //Llamada el servicio rest
                    PostAlimento postPeso = new PostAlimento(alimentoDTO);
                    postPeso.execute();
                }

            } catch (Exception e) {
                Toast.makeText(IngresoAlimentoActivity.this, "No se pudo completar el registro", Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(IngresoAlimentoActivity.this, "Es necesario ingresar todos los campos", Toast.LENGTH_LONG).show();

        }
    }

    private class PostAlimento extends AsyncTask<Void, Void, Boolean> {
        boolean result = true;

        private AlimentoDTO unAlimento;

        private PostAlimento(AlimentoDTO alimentoDTO) {

            unAlimento = alimentoDTO;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {

                AlimentoDTO unAlimentoDTO = new AlimentoDTO();
                unAlimentoDTO = unAlimento;

                String request = "http://192.168.1.2:8081/PFT-Crianza/rest/alimento/ingresoAlimento";

                final URL url = new URL(request);
                final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setInstanceFollowRedirects(false);
                connection.setRequestMethod("POST");
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

                }else {
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
                Toast.makeText(IngresoAlimentoActivity.this, "Alimento Ingresado", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(IngresoAlimentoActivity.this, "No se completó el registro, Por favor verifique el nombre del alimento", Toast.LENGTH_LONG).show();
            }
        }
    }



}
