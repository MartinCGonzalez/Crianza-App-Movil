package com.crianza.activities;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.crianza.R;
import com.crianza.dtos.PesoDTO;
import com.crianza.fragments.TerneroGananciaPesoFragment;
import com.crianza.validaciones.ValidacionDatos;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class IngresoGananciaPesoActivity extends AppCompatActivity {

    Spinner sp_tipoRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_registro_peso);

        final Calendar myCalendar = Calendar.getInstance();

        final EditText edittext = (EditText) findViewById(R.id.dte_FechaDesde);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                edittext.setText(sdf.format(myCalendar.getTime()));
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(IngresoGananciaPesoActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //se carga el spinner de tipo registro
        cargarSpTipoRegistro();

        //Se llama al administrador de fragmentos y mediante el método add() se agrega la instancia de TerneroGananciaPesoFragment.
        TerneroGananciaPesoFragment ternerosFragment = (TerneroGananciaPesoFragment)
                getSupportFragmentManager().findFragmentById(R.id.terneros_container);

        if (ternerosFragment == null) {
            ternerosFragment = TerneroGananciaPesoFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.registroPeso_container, ternerosFragment)
                    .commit();
        }
    }

    //Método para cargar spinner del tipo registro
    private void cargarSpTipoRegistro() {

        //obtiene la referencia al spinner estadoPedido
        sp_tipoRegistro = (Spinner) findViewById(R.id.sp_tipoRegistro);

        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource(this, R.array.tipoRegistro, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_tipoRegistro.setAdapter(spinner_adapter);


    }

    public void ingresoPeso(View view) throws ParseException {

        EditText txtIdT = (EditText) findViewById(R.id.txtIdTernero);
        EditText txtPeso = (EditText) findViewById(R.id.txtPeso);
        EditText fecha1 = (EditText) findViewById(R.id.dte_FechaDesde);
        String nombre = sp_tipoRegistro.getSelectedItem().toString();

        String idT = txtIdT.getText().toString();
        String fecha = fecha1.getText().toString();
        String peso = txtPeso.getText().toString();
        String nom = nombre;


        if(!fecha.isEmpty() && !peso.isEmpty() && !nom.isEmpty() && !idT.isEmpty()) {

            Long idTernero = Long.parseLong(txtIdT.getText().toString());
            BigDecimal pesoTernero = BigDecimal.valueOf(Double.valueOf(peso));

            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            Date fechaDate = format.parse(fecha);

            try {
                if (ValidacionDatos.validarPeso(pesoTernero, fechaDate)) {

                    PesoDTO pesoDTO = new PesoDTO(fecha, pesoTernero, nombre, idTernero);

                    PostGananciaPeso postPeso = new PostGananciaPeso(pesoDTO);
                    postPeso.execute();

                }

            } catch (Exception e) {
                Toast.makeText(IngresoGananciaPesoActivity.this, "No se pudo completar el registro", Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(IngresoGananciaPesoActivity.this, "Es necesario ingresar todos los campos", Toast.LENGTH_LONG).show();

        }
    }

    //Metodo para registrar la ganancia de peso
    private class PostGananciaPeso extends AsyncTask<Void, Void, Boolean> {
        boolean result = true;

        private PesoDTO peso;

        private PostGananciaPeso(PesoDTO pesoDTO) {

            peso = pesoDTO;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {

                PesoDTO unPesoDTO = new PesoDTO();
                unPesoDTO = peso;

                String request = "http://192.168.1.2:8081/PFT-Crianza/rest/peso/ingresoGananciaPeso";

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
                outputStreamWriter.write(gson.toJson(unPesoDTO));
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
                Toast.makeText(IngresoGananciaPesoActivity.this, "Registro Completo", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(IngresoGananciaPesoActivity.this, "Error al conectar con servidor, Por favor contacte a su administrador", Toast.LENGTH_LONG).show();


            }
        }
    }

}
