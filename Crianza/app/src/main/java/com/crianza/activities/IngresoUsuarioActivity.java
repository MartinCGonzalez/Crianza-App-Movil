package com.crianza.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.crianza.R;
import com.crianza.dtos.UsuarioDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;

import static com.crianza.utils.ConnectivityHelper.isConnected;


public class IngresoUsuarioActivity extends AppCompatActivity {

    Spinner sp_tipoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_usuario);

        //se carga el spinner de tipo perfil
        cargarSpTipoPerfil();

        //Check de conexión a internet
        if (isConnected(this)) {

        } else {
            Toast.makeText(IngresoUsuarioActivity.this, "Sin Conexión a Internet", Toast.LENGTH_SHORT).show();
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
        builder.setMessage("Es necesario ingresar los dos nombres y los dos apellidos.\n")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

    }

    //Método para cargar spinner de tipo perfil
    private void cargarSpTipoPerfil() {

        //obtiene la referencia al spinner tipoPerfil
        sp_tipoPerfil = (Spinner) findViewById(R.id.sp_tipoPerfil);

        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource(this, R.array.tipoPerfil, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_tipoPerfil.setAdapter(spinner_adapter);

    }
    public void ingresoUsuario(View view) throws ParseException {

        EditText txtNombres = (EditText) findViewById(R.id.txtNombres);
        EditText txtApellidos = (EditText) findViewById(R.id.txtApellidos);
        EditText txtContrasenia = (EditText) findViewById(R.id.txtContrasenia);

        String nombres = txtNombres.getText().toString();
        String apellidos = txtApellidos.getText().toString();
        String contrasenia = txtContrasenia.getText().toString();
        String perfil = sp_tipoPerfil.getSelectedItem().toString();

        if (!nombres.isEmpty() && !apellidos.isEmpty() && !contrasenia.isEmpty() && !perfil.isEmpty()) {

        try {
                //Carga de datos al DTO
                UsuarioDTO usuarioDTO = new UsuarioDTO(nombres, apellidos, perfil, contrasenia);

                //Llamada el servicio rest
                PostUsuario postPeso = new PostUsuario(usuarioDTO);
                postPeso.execute();

        } catch (Exception e) {
            Toast.makeText(IngresoUsuarioActivity.this, "No se pudo completar el registro", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        } else {
            Toast.makeText(IngresoUsuarioActivity.this, "Es necesario ingresar todos los campos", Toast.LENGTH_LONG).show();
        }
    }

    //Metodo para registrar la ganancia de peso
    private class PostUsuario extends AsyncTask<Void, Void, Boolean> {
        boolean result = true;

        private UsuarioDTO usuario;

        private PostUsuario(UsuarioDTO usuarioDTO) {

            usuario = usuarioDTO;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {

                UsuarioDTO unUsuarioDTO = new UsuarioDTO();
                unUsuarioDTO = usuario;

                String request = "http://192.168.1.2:8081/PFT-Crianza/rest/usuario/ingresoUsuario";

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
                outputStreamWriter.write(gson.toJson(unUsuarioDTO));
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

                Toast.makeText(IngresoUsuarioActivity.this, "Registro Completo", Toast.LENGTH_LONG).show();


            } else {

                Toast.makeText(IngresoUsuarioActivity.this, "Error al ingresar el usuario, Corroborar Nombres y/o Apellidos", Toast.LENGTH_LONG).show();
//                Toast.makeText(IngresoUsuarioActivity.this, "Error al conectar con servidor, Por favor contacte a su administrador", Toast.LENGTH_LONG).show();


            }
        }
    }

}
