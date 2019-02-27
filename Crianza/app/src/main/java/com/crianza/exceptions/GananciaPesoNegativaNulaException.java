package com.crianza.exceptions;

import android.content.Context;
import android.widget.Toast;

import com.crianza.utils.GlobalApplication;

@SuppressWarnings("serial")
public class GananciaPesoNegativaNulaException extends Exception {

	public GananciaPesoNegativaNulaException () {
		Context context = GlobalApplication.getAppContext();

		Toast.makeText(context, "El ternero no muestra ganancia de peso frente a registro anterior, se recomienda dar una revision inmediata", Toast.LENGTH_LONG).show();

	}
}
