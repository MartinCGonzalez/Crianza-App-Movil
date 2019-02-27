package com.crianza.exceptions;

import android.content.Context;
import android.widget.Toast;

import com.crianza.utils.GlobalApplication;

@SuppressWarnings("serial")
public class FormatoFechaNoValido extends Exception {
	
	public FormatoFechaNoValido () {
		Context context = GlobalApplication.getAppContext();
		Toast.makeText(context, "El formato de la fecha debe ser DD/MM/YYYY", Toast.LENGTH_LONG).show();

	}

}
