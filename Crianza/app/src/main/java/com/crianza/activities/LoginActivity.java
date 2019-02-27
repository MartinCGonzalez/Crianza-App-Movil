package com.crianza.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crianza.R;
import com.crianza.dtos.UsuarioDTO;
import com.crianza.utils.GlobalApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.crianza.utils.ConnectivityHelper.isConnected;


public class LoginActivity extends AppCompatActivity {

    List<UsuarioDTO> usuarioDTOList = new ArrayList<>();
    TextView statusTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Check de conexión a internet
        if (isConnected(this)) {
            Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(LoginActivity.this, "Sin Conexión a Internet", Toast.LENGTH_SHORT).show();
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

    public void onBackPressed() {
        moveTaskToBack(true);
        isConnected(this);

    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (!hasFocus) {
            Log.i("Tag", "Notification bar is pulled down");
        } else {
            Log.i("Tag", "Notification bar is pushed up");
            isConnected(this);
            }
        super.onWindowFocusChanged(hasFocus);

    }

    public void validarLogin(View view) {

        try {

            ValidarLoginTask postUsuario = new ValidarLoginTask(usuarioDTOList);
            postUsuario.execute();


        }catch (Exception e){
            Toast.makeText(LoginActivity.this, "Usuario/Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    private class ValidarLoginTask extends AsyncTask<Void,Void,Boolean> {

        List<UsuarioDTO> listado = new ArrayList<>();

        private ValidarLoginTask(List<UsuarioDTO> usuarioDTO) { this.listado = usuarioDTO; }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean result = true;
            URL url;
            HttpURLConnection urlConnection = null;


            try {

                EditText txtUsuario = (EditText) findViewById(R.id.txtUsuario);
                EditText txtContraseña = (EditText) findViewById(R.id.txtContrasenia);

                String usuario = txtUsuario.getText().toString().trim();
                String contraseña = txtContraseña.getText().toString();

                String urlServicio = "http://192.168.1.2:8081/PFT-Crianza/rest/usuario/loginUsuario/"+ usuario + "/" +contraseña;
                url = new URL(urlServicio);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("content-type", "application/json;charset = utf-8");

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                JSONArray respJSON = new JSONArray(getResponseText(in));

                //Se recorre el Json y se obtienen los datos a agregar al listado
                for(int i=0; i<respJSON.length(); i++) {
                    JSONObject obj = respJSON.getJSONObject(i);
                    String usuario1 = obj.getString("usuario");
                    String contra = obj.getString("contraseña");
                    String perfil = obj.getString("perfil");

                    UsuarioDTO usr = new UsuarioDTO(usuario1, contra, perfil);

                    listado.add(usr);
                }
                int status = urlConnection.getResponseCode();


                if(listado.isEmpty()){
                    return false;
                }


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

                for (UsuarioDTO u : usuarioDTOList) {

                    //Compara el perfil y despliega la actividad
                    if (u.getPerfil().toUpperCase().equals("ADMINISTRADOR")) {

                        Context context = GlobalApplication.getAppContext();

                        Intent intent = new Intent(context, MainAdministradorActivity.class);
                        startActivity(intent);

                    } else if (u.getPerfil().toUpperCase().equals("PERSONAL")) {

                        Context context = GlobalApplication.getAppContext();

                        Intent intent = new Intent(context, MainPersonalActivity.class);
                        startActivity(intent);

                    } else {

                        Context context = GlobalApplication.getAppContext();

                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);

                    }
                }

            } else {

                Toast.makeText(LoginActivity.this, "Usuario/Contraseña Incorrectos", Toast.LENGTH_LONG).show();

            }
        }

        private String getResponseText(InputStream inStream) {
            // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
            return new Scanner(inStream).useDelimiter("\\A").next();
        }
    }



}