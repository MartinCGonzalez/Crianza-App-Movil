package com.crianza.exceptions;

import android.content.Context;
import android.widget.Toast;

import com.crianza.utils.GlobalApplication;

@SuppressWarnings("serial")
public class StockInsuficienteException extends Exception {
	public StockInsuficienteException () {
		Context context = GlobalApplication.getAppContext();

		Toast.makeText(context, "No hay suficiente stock del alimento seleccionado", Toast.LENGTH_LONG).show();

	}
}
