package com.crianza.exceptions;

import android.content.Context;
import android.widget.Toast;

import com.crianza.utils.GlobalApplication;


@SuppressWarnings("serial")
public class IdExistente extends Exception{
	
	public IdExistente () {
		Context context = GlobalApplication.getAppContext();

		Toast.makeText(context, "Alimento ya ingresado, Prueba Actualizarlo!", Toast.LENGTH_LONG).show();


	}
}

