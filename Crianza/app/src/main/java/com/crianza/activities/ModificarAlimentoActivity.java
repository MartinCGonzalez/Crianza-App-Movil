package com.crianza.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.crianza.R;
import com.crianza.dtos.AlimentoDTO;
import com.crianza.fragments.AlimentosFragment;
import com.crianza.validaciones.ValidacionDatos;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.crianza.utils.ConnectivityHelper.isConnected;


public class ModificarAlimentoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_alimento);

        //Se llama al administrador de fragmentos y mediante el método add() se agrega la instancia de AlimentosFragment.
        AlimentosFragment alimentosFragment = (AlimentosFragment)
                getSupportFragmentManager().findFragmentById(R.id.modificarAlimento_container);

        if (alimentosFragment == null) {
            alimentosFragment = AlimentosFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.modificarAlimento_container, alimentosFragment)
                    .commit();
        }

        //Check de conexión a internet
        if (isConnected(this)) {
//            Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(ModificarAlimentoActivity.this, "Sin Conexión a Internet", Toast.LENGTH_SHORT).show();
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

    public void help(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Seleccione el alimento a modificar de la lista y modifique los valores deseados.\n")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

    }

    public void modificarAlimento(View view) {

        EditText txtId = (EditText)  findViewById(R.id.txtID);
        EditText txtCostoUnidad = (EditText)  findViewById(R.id.txtCostoUnidad);
        EditText txtCantidad = (EditText) findViewById(R.id.txtCantidad);

        //Cargo los campos en String para verificar que esten cargados
        String idA = txtId.getText().toString();
        String cantidad = txtCantidad.getText().toString();
        String costoUnidad = txtCostoUnidad.getText().toString();

        //Verifico campos ingresados
        if (!idA.isEmpty() && !cantidad.isEmpty() && !costoUnidad.isEmpty()) {

            //Transformo los datos para el insert
            Long idAlimento = Long.parseLong(txtId.getText().toString());
            BigDecimal cantidadAli = BigDecimal.valueOf(Double.valueOf(cantidad));
            BigDecimal costo = BigDecimal.valueOf(Double.valueOf(costoUnidad));

            //Validaciones
            if (ValidacionDatos.validarActualizacion(cantidadAli, costo)) {

                //Carga de datos al DTO
                AlimentoDTO unAlimentoDTO = new AlimentoDTO(idAlimento, cantidadAli, costo);

                //Llamada el servicio rest
                PutAlimento postAlimento = new PutAlimento(unAlimentoDTO);
                postAlimento.execute();
            }

        } else {
            Toast.makeText(ModificarAlimentoActivity.this, "Es necesario ingresar todos los campos", Toast.LENGTH_LONG).show();

        }


    }

    //Metodo para modificar alimento
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

                String request = "http://192.168.1.2:8081/PFT-Crianza/rest/alimento/modificarAlimento";

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
                Toast.makeText(ModificarAlimentoActivity.this, "Alimento Modificado", Toast.LENGTH_SHORT).show();

            } else {
//                Toast.makeText(ModificarAlimentoActivity.this, "Alimento No Modificado", Toast.LENGTH_LONG).show();
                Toast.makeText(ModificarAlimentoActivity.this, "Error al conectar con servidor, Por favor contacte a su administrador", Toast.LENGTH_LONG).show();


            }
        }
    }
}
