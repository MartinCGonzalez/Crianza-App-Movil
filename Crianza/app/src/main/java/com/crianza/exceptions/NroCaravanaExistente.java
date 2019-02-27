package com.crianza.exceptions;

import android.content.Context;
import android.widget.Toast;

import com.crianza.utils.GlobalApplication;

@SuppressWarnings("serial")
public class NroCaravanaExistente extends Exception {
	
	public NroCaravanaExistente () {
		Context context = GlobalApplication.getAppContext();

		Toast.makeText(context, "Numero de Caravana ya ingresado!", Toast.LENGTH_LONG).show();

	}
}
