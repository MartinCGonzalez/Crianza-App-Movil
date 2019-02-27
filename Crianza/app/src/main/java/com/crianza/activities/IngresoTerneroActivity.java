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
import com.crianza.dtos.TerneroDTO;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.crianza.utils.ConnectivityHelper.isConnected;

public class IngresoTerneroActivity extends AppCompatActivity {

    Spinner sp_tipoParto;
    Spinner sp_tipoRaza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_ternero);

        final Calendar myCalendar = Calendar.getInstance();

        final EditText fechaNac = (EditText) findViewById(R.id.dte_FechaNacimiento);
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

                fechaNac.setText(sdf.format(myCalendar.getTime()));
            }

        };

        fechaNac.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(IngresoTerneroActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //se carga el spinner de tipo raza
        cargarSpTipoRaza();

        //se carga el spinner de tipo parto
        cargarSpTipoParto();
        //Check de conexión a internet
        if (isConnected(this)) {
//            Toast.makeText(AgregarAlimentoActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(IngresoTerneroActivity.this, "Sin Conexión a Internet", Toast.LENGTH_SHORT).show();
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

    //Método para cargar spinner de tipo raza
    private void cargarSpTipoRaza() {

        //obtiene la referencia al spinner tipoRaza
        sp_tipoRaza = (Spinner) findViewById(R.id.sp_tipoRaza);

        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource(this, R.array.tipoRaza, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_tipoRaza.setAdapter(spinner_adapter);


    }

    //Método para cargar spinner de tipo parto
    private void cargarSpTipoParto() {

        //obtiene la referencia al spinner tipoParto
        sp_tipoParto = (Spinner) findViewById(R.id.sp_tipoParto);

        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource(this, R.array.tipoParto, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_tipoParto.setAdapter(spinner_adapter);


    }

    public void ingresoTernero(View view) throws ParseException {

        EditText txtNroCar = (EditText) findViewById(R.id.txtNroCaravana);
        EditText txtIdM = (EditText) findViewById(R.id.txtIdMadre);
        EditText txtIdP = (EditText) findViewById(R.id.txtIdPadre);
        EditText txtIdG = (EditText) findViewById(R.id.txtIdGuachera);
        EditText txtPeso = (EditText) findViewById(R.id.txtPeso);
        EditText txtfechaNacimiento = (EditText) findViewById(R.id.dte_FechaNacimiento);

        //Cargo los campos en String para verificar que esten cargados
        String nroCar = txtNroCar.getText().toString();
        String idM = txtIdM.getText().toString();
        String idP = txtIdP.getText().toString();
        String idG = txtIdG.getText().toString();
        String raza = sp_tipoRaza.getSelectedItem().toString();
        String parto = sp_tipoParto.getSelectedItem().toString();
        String fec = txtfechaNacimiento.getText().toString();
        String peso = txtPeso.getText().toString();

        //Verifico campos ingresados
        if (!nroCar.isEmpty() && !idM.isEmpty() && !idP.isEmpty() && !idG.isEmpty() && !fec.isEmpty() && !peso.isEmpty()){

            //Transformo los datos para el insert
            Long nroCaravana = Long.parseLong(txtNroCar.getText().toString());
            Long idMadre = Long.parseLong(txtIdM.getText().toString());
            Long idPadre = Long.parseLong(txtIdP.getText().toString());
            Long idGuachera = Long.parseLong(txtIdG.getText().toString());

            BigDecimal pesoNac = BigDecimal.valueOf(Double.valueOf(peso));

            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date fecha = format.parse(fec);

        try {

            //Validaciones
            if(ValidacionDatos.validarTernero(pesoNac, fecha)) {

                //Carga de datos al DTO
                TerneroDTO terneroDTO = new TerneroDTO(nroCaravana,idMadre, idPadre, idGuachera, pesoNac, fec, parto, raza);

                //Llamada el servicio rest
                PostTernero postPeso = new PostTernero(terneroDTO);
                postPeso.execute();

                }

        } catch (Exception e) {
            Toast.makeText(IngresoTerneroActivity.this, "No se pudo completar el registro", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        } else {
            Toast.makeText(IngresoTerneroActivity.this, "Es necesario ingresar todos los campos", Toast.LENGTH_LONG).show();

        }

    }
    //Metodo para registrar la ganancia de peso
    private class PostTernero extends AsyncTask<Void, Void, Boolean> {
        boolean result = true;

        private TerneroDTO ternero;

        private PostTernero(TerneroDTO terneroDTO) {

            ternero = terneroDTO;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {

                TerneroDTO unTerneroDTO = new TerneroDTO();
                unTerneroDTO = ternero;

                String request = "http://192.168.1.2:8081/PFT-Crianza/rest/ternero/ingresoTernero";

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
                outputStreamWriter.write(gson.toJson(unTerneroDTO));
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
                Toast.makeText(IngresoTerneroActivity.this, "Ternero Ingresado", Toast.LENGTH_SHORT).show();

            } else {
//                Toast.makeText(IngresoTerneroActivity.this, "Ternero No ingresado, Corroborar NroCaravana", Toast.LENGTH_LONG).show();
                Toast.makeText(IngresoTerneroActivity.this, "Error al conectar con servidor, Por favor contacte a su administrador", Toast.LENGTH_LONG).show();


            }
        }
    }

}