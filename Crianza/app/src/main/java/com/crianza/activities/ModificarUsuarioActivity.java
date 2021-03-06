package com.crianza.activities;

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
import com.crianza.fragments.UsuariosFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.crianza.utils.ConnectivityHelper.isConnected;


public class ModificarUsuarioActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_usuario);

        //Se llama al administrador de fragmentos y mediante el método add() se agrega la instancia de UsuariosFragment.
        UsuariosFragment usuariosFragment = (UsuariosFragment)
                getSupportFragmentManager().findFragmentById(R.id.modificarUsuario_container);

        if (usuariosFragment == null) {
            usuariosFragment = UsuariosFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.modificarUsuario_container, usuariosFragment)
                    .commit();
        }
        //Check de conexión a internet
        if (isConnected(this)) {
//            Toast.makeText(AgregarAlimentoActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(ModificarUsuarioActivity.this, "Sin Conexión a Internet", Toast.LENGTH_SHORT).show();
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


    //Metodo para modificar un usuario
    public void modificarUsuario(View view) {

        EditText txtId = (EditText) findViewById(R.id.txtIdUsuario);
        EditText txtContrasenia = (EditText) findViewById(R.id.txtContrasenia);

        String id = txtId.getText().toString();
        String contrasenia = txtContrasenia.getText().toString();

        if (!id.isEmpty() && !contrasenia.isEmpty()) {

            Long idUsuario = Long.parseLong(txtId.getText().toString());

            try {

                UsuarioDTO unUsuarioDTO = new UsuarioDTO(idUsuario, contrasenia);

                PutUsuario putUsuario = new PutUsuario(unUsuarioDTO);
                putUsuario.execute();

            } catch (Exception e) {
                Toast.makeText(ModificarUsuarioActivity.this, "No se pudo completar el registro", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        } else {
            Toast.makeText(ModificarUsuarioActivity.this, "Es necesario ingresar todos los campos", Toast.LENGTH_LONG).show();


        }
    }

    //Metodo para modificar alimento
    private class PutUsuario extends AsyncTask<Void, Void, Boolean> {
        boolean result = true;

        private UsuarioDTO unUsuario;

        private PutUsuario(UsuarioDTO usuarioDTO) {

            unUsuario = usuarioDTO;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {

                UsuarioDTO unUsuarioDTO = new UsuarioDTO();
                unUsuarioDTO = unUsuario;

                String request = "http://192.168.1.2:8081/PFT-Crianza/rest/usuario/modificarUsuario";

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
                Toast.makeText(ModificarUsuarioActivity.this, "Usuario Modificado", Toast.LENGTH_SHORT).show();

            } else {
//                Toast.makeText(ModificarUsuarioActivity.this, "Usuario No Modificado", Toast.LENGTH_LONG).show();
                Toast.makeText(ModificarUsuarioActivity.this, "Error al conectar con servidor, Por favor contacte a su administrador", Toast.LENGTH_LONG).show();


            }
        }
    }

}