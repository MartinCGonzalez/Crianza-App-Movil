package com.crianza.exceptions;

import android.content.Context;
import android.widget.Toast;

import com.crianza.utils.GlobalApplication;

@SuppressWarnings("serial")
public class FechaFuturoException extends Exception {
	
	public FechaFuturoException () {
		Context context = GlobalApplication.getAppContext();

		Toast.makeText(context, "La fecha no puede ser mayor a hoy o menor al año corriente!", Toast.LENGTH_LONG).show();

	}

}
