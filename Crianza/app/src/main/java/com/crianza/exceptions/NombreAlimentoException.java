package com.crianza.exceptions;

import android.content.Context;
import android.widget.Toast;

import com.crianza.utils.GlobalApplication;

@SuppressWarnings("serial")

public class NombreAlimentoException extends Exception {

	public NombreAlimentoException() {
		Context context = GlobalApplication.getAppContext();

		Toast.makeText(context, "El nombre del alimento es incorrecto!", Toast.LENGTH_LONG).show();

	}
}
