package com.crianza.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;

public class ConnectivityHelper {

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected())) {
            return true;
        } else {
            Toast.makeText(GlobalApplication.getAppContext(), "Sin Conexión a Internet", Toast.LENGTH_SHORT).show();
            showDialog(context);
            return false;
        }
    }

    private static void showDialog(Context context) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Es necesaria una conexión a internet para continuar")
                .setCancelable(false)
                .setNegativeButton("Configuración", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GlobalApplication.getAppContext().startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNeutralButton("Salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((Activity)GlobalApplication.getAppContext()).finish();
                    }
                });


        if (! ((Activity) context).isFinishing()) {
            AlertDialog alert = builder.create();
            alert.show();

        }

    }
}