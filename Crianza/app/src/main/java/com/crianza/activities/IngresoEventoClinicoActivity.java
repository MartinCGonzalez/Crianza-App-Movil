package com.crianza.activities;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.crianza.R;
import com.crianza.dtos.EventoClinicoDTO;
import com.crianza.fragments.EnfermedadEventoClinicoFragment;
import com.crianza.fragments.TerneroGananciaPesoFragment;
import com.crianza.validaciones.ValidacionDatos;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.crianza.utils.ConnectivityHelper.isConnected;

public class IngresoEventoClinicoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_evento_clinico);

        final Calendar myCalendar = Calendar.getInstance();

        final EditText fechaDesde = (EditText) findViewById(R.id.dte_FechaDesde);

        final EditText fechaHasta = (EditText) findViewById(R.id.dte_FechaHasta);

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

                fechaDesde.setText(sdf.format(myCalendar.getTime()));

            }

        };

        final DatePickerDialog.OnDateSetListener dateHasta = new DatePickerDialog.OnDateSetListener() {

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

                fechaHasta.setText(sdf.format(myCalendar.getTime()));

            }

        };

        fechaDesde.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(IngresoEventoClinicoActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        fechaHasta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(IngresoEventoClinicoActivity.this, dateHasta, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //Se llama al administrador de fragmentos y mediante el método add() se agrega la instancia de TerneroGananciaPesoFragment.
        TerneroGananciaPesoFragment ternerosFragment = (TerneroGananciaPesoFragment)
                getSupportFragmentManager().findFragmentById(R.id.terneros_container);

        EnfermedadEventoClinicoFragment enfermedadFragment = (EnfermedadEventoClinicoFragment)
                getSupportFragmentManager().findFragmentById(R.id.enfermedades_container);

        if (ternerosFragment == null && enfermedadFragment == null) {
            ternerosFragment = TerneroGananciaPesoFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.ingresoEventoClinico_container, ternerosFragment)
                    .commit();

            enfermedadFragment = EnfermedadEventoClinicoFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.ingresoEventoClinico_container, enfermedadFragment)
                    .commit();
        }

    //Check de conexión a internet
        if (isConnected(this)) {
//            Toast.makeText(AgregarAlimentoActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();

    } else {
        Toast.makeText(IngresoEventoClinicoActivity.this, "Sin Conexión a Internet", Toast.LENGTH_SHORT).show();
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


    public void registroEventoClinico(View view) throws ParseException {

        EditText txtIdT = (EditText) findViewById(R.id.txtIdTernero);
        EditText txtIdE = (EditText) findViewById(R.id.txtIdEnfermedad);
        EditText fechaDesde = (EditText) findViewById(R.id.dte_FechaDesde);
        EditText fechaHasta = (EditText) findViewById(R.id.dte_FechaHasta);
        EditText txtObs = (EditText) findViewById(R.id.txtObservaciones);

        //Cargo los campos en String para verificar que esten cargados
        String idT = txtIdT.getText().toString();
        String idE = txtIdE.getText().toString();
        String fechDes = fechaDesde.getText().toString();
        String fechHas = fechaHasta.getText().toString();
        String observacion = txtObs.getText().toString();

        //Verifico campos ingresados
        if (!idT.isEmpty() && !idE.isEmpty() && !fechDes.isEmpty()) {

            //Transformo los datos para el insert
            Long idTernero = Long.parseLong(txtIdT.getText().toString());
            Long idEnfermedad = Long.parseLong(txtIdE.getText().toString());

            //Doy formato a la fecha para la validacion
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date fecDesde = format.parse(fechDes);

            //Verifico que los campos no esten vacios al hacer parse
            if (idTernero != null && idEnfermedad != null && fechaDesde != null && fechHas.isEmpty()) {

                try {

                    //Validaciones
                    if (ValidacionDatos.validarEventoClinico(observacion, fecDesde)) {

                        //Carga de datos al DTO
                        EventoClinicoDTO eventoClinicoDTO = new EventoClinicoDTO(idEnfermedad, idTernero, fechDes, observacion);

                        //Llamada el servicio rest
                        PostEventoClinico postEventoClinico = new PostEventoClinico(eventoClinicoDTO);
                        postEventoClinico.execute();

                    }
                } catch (Exception e) {
                    Toast.makeText(IngresoEventoClinicoActivity.this, "No se pudo completar el registro", Toast.LENGTH_LONG).show();

                }
            }
        } else {
            Toast.makeText(IngresoEventoClinicoActivity.this, "Es necesario ingresar todos los campos", Toast.LENGTH_LONG).show();

        }

        //Verifico campos ingresados
        if (!idT.isEmpty() && !idE.isEmpty() && !fechDes.isEmpty() && !fechHas.isEmpty()) {

            //Transformo los datos para el insert
            Long idTernero = Long.parseLong(txtIdT.getText().toString());
            Long idEnfermedad = Long.parseLong(txtIdE.getText().toString());

            //Doy formato a la fecha para la validacion
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date fecDesde = format.parse(fechDes);

            //Verifico que los campos no esten vacios al hacer parse
            if (idTernero != null && idEnfermedad != null && fechaDesde != null && fechHas.length() > 1) {


                try {

                    Date fecHasta = format.parse(fechHas);

                    //Validaciones
                    if (ValidacionDatos.validarEventoClinico2(fecDesde, fecHasta, observacion)) {

                        //Carga de datos al DTO
                        EventoClinicoDTO eventoClinicoDTO = new EventoClinicoDTO(idEnfermedad, idTernero, fechDes, fechHas, observacion);

                        try {

                            //Compruebo que la fecha Hasta no sea menor a Desde
                            if (fecHasta.before(fecDesde)) {
                                Toast.makeText(IngresoEventoClinicoActivity.this, "La fecha Desde no puede ser menor a la fecha Hasta", Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception e) {
                            Toast.makeText(IngresoEventoClinicoActivity.this, "No se pudo completar el registro", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                        //Llamada el servicio rest
                        PostEventoClinico postEventoClinico = new PostEventoClinico(eventoClinicoDTO);
                        postEventoClinico.execute();


                    }
                } catch (Exception e) {
                    Toast.makeText(IngresoEventoClinicoActivity.this, "No se pudo completar el registro", Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }
            } else {
                Toast.makeText(IngresoEventoClinicoActivity.this, "Es necesario ingresar todos los campos", Toast.LENGTH_LONG).show();

            }
        }
    }

    //Metodo para registrar el evento clinico
    private class PostEventoClinico extends AsyncTask<Void, Void, Boolean> {
        boolean result = true;

        private EventoClinicoDTO eventoClinico;

        private PostEventoClinico(EventoClinicoDTO eventoClinicoDTO) {

            eventoClinico = eventoClinicoDTO;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {

                EventoClinicoDTO unEventoClinicoDTO = new EventoClinicoDTO();
                unEventoClinicoDTO = eventoClinico;

                String request = "http://192.168.1.2:8081/PFT-Crianza/rest/eventoClinico/ingresoEventoClinico";

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
                outputStreamWriter.write(gson.toJson(unEventoClinicoDTO));
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
                Toast.makeText(IngresoEventoClinicoActivity.this, "Registro Ingresado", Toast.LENGTH_SHORT).show();

            } else {
//                Toast.makeText(IngresoEventoClinicoActivity.this, "Error al conectar con servicio REST", Toast.LENGTH_LONG).show();
                Toast.makeText(IngresoEventoClinicoActivity.this, "Error al conectar con servidor, Por favor contacte a su administrador", Toast.LENGTH_LONG).show();


            }
        }
    }

}
