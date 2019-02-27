package com.crianza.exceptions;

import android.content.Context;
import android.widget.Toast;

import com.crianza.utils.GlobalApplication;

@SuppressWarnings("serial")
public class LargoDecimalException extends Exception {
	
	public LargoDecimalException () {
		Context context = GlobalApplication.getAppContext();

		Toast.makeText(context, "Debe ingresar un numero que no contenga mas de dos decimales", Toast.LENGTH_LONG).show();

	}
}
