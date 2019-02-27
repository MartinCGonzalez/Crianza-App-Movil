package com.crianza.exceptions;

import android.content.Context;
import android.widget.Toast;

import com.crianza.utils.GlobalApplication;

@SuppressWarnings("serial")
public class EnfermedadException extends Exception {

	public EnfermedadException () {
		Context context = GlobalApplication.getAppContext();

		Toast.makeText(context, "Codigo de enfermedad incorrecto!", Toast.LENGTH_LONG).show();

	}
}
