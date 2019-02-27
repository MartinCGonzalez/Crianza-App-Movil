package com.crianza.exceptions;

import android.content.Context;
import android.widget.Toast;

import com.crianza.utils.GlobalApplication;

@SuppressWarnings("serial")
public class NumeroNegativoException extends Exception {
	
	public NumeroNegativoException () {
		Context context = GlobalApplication.getAppContext();

		Toast.makeText(context, "El valor ingresado no puede ser negativo o igual a 0", Toast.LENGTH_LONG).show();

	}
}