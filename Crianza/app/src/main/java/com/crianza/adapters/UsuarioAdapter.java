package com.crianza.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.crianza.R;
import com.crianza.dtos.TerneroDTO;
import com.crianza.dtos.UsuarioDTO;

import java.util.List;

public class UsuarioAdapter extends ArrayAdapter<UsuarioDTO> {
    public UsuarioAdapter(Context context, List<UsuarioDTO> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_usuario,
                    parent,
                    false);
        }

        // Referencias UI.
        TextView id = (TextView) convertView.findViewById(R.id.itm_idUsuario);
        TextView nombres = (TextView) convertView.findViewById(R.id.itm_nombres);
        TextView apellidos = (TextView) convertView.findViewById(R.id.itm_apellidos);
        TextView perfil = (TextView) convertView.findViewById(R.id.itm_perfil);
        TextView usuario = (TextView) convertView.findViewById(R.id.itm_usuario);
        TextView contrasenia = (TextView) convertView.findViewById(R.id.itm_contrasenia);

        UsuarioDTO usuarioDTO = getItem(position);

        id.setText("ID: " +usuarioDTO.getIdUsuario().toString());
        nombres.setText("Nombres: " +usuarioDTO.getNombre());
        apellidos.setText("Apellidos: " +usuarioDTO.getApellido());
        perfil.setText("Perfil: " +usuarioDTO.getPerfil());
        usuario.setText("Usuario: " +usuarioDTO.getUsuario());
        contrasenia.setText("Contraseña: " +usuarioDTO.getContraseña());


        return convertView;
    }
}