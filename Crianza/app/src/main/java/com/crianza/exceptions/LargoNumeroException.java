package com.crianza.exceptions;

import android.content.Context;
import android.widget.Toast;

import com.crianza.utils.GlobalApplication;


@SuppressWarnings("serial")
public class LargoNumeroException extends Exception {

	public LargoNumeroException () {
		Context context = GlobalApplication.getAppContext();

		Toast.makeText(context, "El largo del numero ingresado excede lo permitido", Toast.LENGTH_SHORT).show();

	}
}
