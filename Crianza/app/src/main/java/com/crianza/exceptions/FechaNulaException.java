package com.crianza.exceptions;

import android.content.Context;
import android.widget.Toast;

import com.crianza.utils.GlobalApplication;

@SuppressWarnings("serial")
public class FechaNulaException extends Exception {
	
	public FechaNulaException () {
		Context context = GlobalApplication.getAppContext();

		Toast.makeText(context, "La Fecha no puede ser Nula", Toast.LENGTH_LONG).show();

	}
}