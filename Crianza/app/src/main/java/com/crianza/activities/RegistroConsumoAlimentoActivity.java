package com.crianza.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.crianza.R;
import com.crianza.dtos.ConsumoAlimentoDTO;
import com.crianza.fragments.AlimentoConsumoAlimentoFragment;
import com.crianza.fragments.TerneroConsumoAlimentoFragment;
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

public class RegistroConsumoAlimentoActivity extends AppCompatActivity {

    ListView alimentosList;
    ListView list_ternero;
    EditText dte_fecha;
    String fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_consumo_alimento);

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
                new DatePickerDialog(RegistroConsumoAlimentoActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //Se llama al administrador de fragmentos y mediante el método add() se agrega la instancia de AlimentosFragment.
        AlimentoConsumoAlimentoFragment alimentosFragment = (AlimentoConsumoAlimentoFragment)
                getSupportFragmentManager().findFragmentById(R.id.consumoAlimento_container);

        TerneroConsumoAlimentoFragment ternerosFragment = (TerneroConsumoAlimentoFragment)
                getSupportFragmentManager().findFragmentById(R.id.terneros_container);

        if (alimentosFragment == null && ternerosFragment == null) {

            alimentosFragment = AlimentoConsumoAlimentoFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.consumoAlimento_container, alimentosFragment)
                    .commit();

            ternerosFragment = TerneroConsumoAlimentoFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.consumoAlimento_container, ternerosFragment)
                    .commit();
        }

        //Check de conexión a internet
        if (isConnected(this)) {
//            Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(RegistroConsumoAlimentoActivity.this, "Sin Conexión a Internet", Toast.LENGTH_SHORT).show();
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


    public void registroConsumoAlimento(View view) throws ParseException {

        EditText txtIdA = (EditText) findViewById(R.id.txtIdAlimento);
        EditText txtIdT = (EditText) findViewById(R.id.txtIdTernero);
        EditText txtCantidad = (EditText) findViewById(R.id.txtCantidad);
        EditText fecha1 = (EditText) findViewById(R.id.dte_FechaDesde);
        EditText stock = (EditText) findViewById(R.id.txtStock);

        String fec = fecha1.getText().toString();

        String cantidad = txtCantidad.getText().toString();
        String stockAlimento = stock.getText().toString();

        Long idAlimento = Long.parseLong(txtIdA.getText().toString());
        Long idTernero = Long.parseLong(txtIdT.getText().toString());

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        Date fecha = format.parse(fec);

        boolean alta = false;

        try {

            BigDecimal cantidadAli = BigDecimal.valueOf(Double.valueOf(cantidad));
            BigDecimal stk = BigDecimal.valueOf(Double.valueOf(stockAlimento));

            double c = cantidadAli.doubleValue();
            double s = stk.doubleValue();

            Double restante = s - c;

            if(c > s) {
                Toast.makeText(RegistroConsumoAlimentoActivity.this, "No cuentas con el stock suficiente !!!", Toast.LENGTH_LONG).show();
                alta = false;
            } else {
                alta = true;
            }

            if (alta) {

                if(ValidacionDatos.validarConsumoAlimento(cantidadAli,fecha)) {

                ConsumoAlimentoDTO consumoAlimentoDTO = new ConsumoAlimentoDTO(idTernero, idAlimento, cantidadAli, fec);

                    if (restante == 0) {

                        Toast.makeText(RegistroConsumoAlimentoActivity.this, "Se agotó el stock!!", Toast.LENGTH_LONG).show();

                    }

                    PostConsumoAlimento postConsumoAlimento = new PostConsumoAlimento(consumoAlimentoDTO);
                    postConsumoAlimento.execute();

                }
            }
        } catch (Exception e) {
            Toast.makeText(RegistroConsumoAlimentoActivity.this, "No se pudo completar el registro", Toast.LENGTH_LONG).show();

        }
    }

    //Metodo para registrar consumo de alimento
    private class PostConsumoAlimento extends AsyncTask<Void, Void, Boolean> {
        boolean result = true;

        private ConsumoAlimentoDTO consumoAlimento;

        private PostConsumoAlimento(ConsumoAlimentoDTO consumoAlimentoDTO) {

            consumoAlimento = consumoAlimentoDTO;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {

                ConsumoAlimentoDTO unConsumoAlimentoDTO = new ConsumoAlimentoDTO();
                unConsumoAlimentoDTO = consumoAlimento;

                String request = "http://192.168.1.2:8081/PFT-Crianza/rest/consumoAlimento/ingresoConsumoAlimento";

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
                outputStreamWriter.write(gson.toJson(unConsumoAlimentoDTO));
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
                Toast.makeText(RegistroConsumoAlimentoActivity.this, "Registo Correcto", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(RegistroConsumoAlimentoActivity.this, "No se pudo completar el registro", Toast.LENGTH_LONG).show();
                Toast.makeText(RegistroConsumoAlimentoActivity.this, "Error al conectar con servidor, Por favor contacte a su administrador", Toast.LENGTH_LONG).show();
            }
        }
    }

}
