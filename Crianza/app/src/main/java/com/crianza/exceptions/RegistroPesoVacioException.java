package com.crianza.exceptions;

import android.content.Context;
import android.widget.Toast;

import com.crianza.utils.GlobalApplication;


@SuppressWarnings("serial")
public class RegistroPesoVacioException extends Exception  {
	
	public RegistroPesoVacioException () {
		Context context = GlobalApplication.getAppContext();

		Toast.makeText(context, "Debe registrar pesos antes para generar informe de ganancia de peso", Toast.LENGTH_LONG).show();

	}
}
