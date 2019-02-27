package com.crianza.exceptions;

import android.content.Context;
import android.widget.Toast;

import com.crianza.utils.GlobalApplication;

@SuppressWarnings("serial")
public class UsuarioException extends Exception {
	
	public UsuarioException () {
		Context context = GlobalApplication.getAppContext();

		Toast.makeText(context, "Usuario ya existente, por favor revise sus datos.", Toast.LENGTH_LONG).show();

	}
}
