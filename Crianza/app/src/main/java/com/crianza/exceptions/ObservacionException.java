package com.crianza.exceptions;

import android.content.Context;
import android.widget.Toast;

import com.crianza.utils.GlobalApplication;

@SuppressWarnings("serial")
public class ObservacionException extends Exception {
	
	public ObservacionException () {
		Context context = GlobalApplication.getAppContext();

		Toast.makeText(context, "No igresar mas de 250 caracteres!", Toast.LENGTH_LONG).show();

	}
}
