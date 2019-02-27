package com.crianza.exceptions;

import android.content.Context;
import android.widget.Toast;

import com.crianza.utils.GlobalApplication;

@SuppressWarnings("serial")
public class TerneroNoVivoException extends Exception {
	
	public TerneroNoVivoException () {
		Context context = GlobalApplication.getAppContext();

		Toast.makeText(context, "El ternero no se encuentra vivo!", Toast.LENGTH_LONG).show();
	}
}
