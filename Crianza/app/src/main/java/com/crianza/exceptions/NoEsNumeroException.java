package com.crianza.exceptions;

import android.content.Context;
import android.widget.Toast;

import com.crianza.utils.GlobalApplication;

@SuppressWarnings("serial")
public class NoEsNumeroException extends Exception {

	public NoEsNumeroException() {
		Context context = GlobalApplication.getAppContext();

		Toast.makeText(context, "La cantidad debe de ser un numero!", Toast.LENGTH_LONG).show();

	}
}
