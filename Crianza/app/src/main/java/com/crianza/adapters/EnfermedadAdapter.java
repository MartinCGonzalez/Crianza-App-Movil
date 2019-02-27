package com.crianza.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.crianza.R;
import com.crianza.dtos.AlimentoDTO;
import com.crianza.dtos.EnfermedadDTO;

import java.util.List;

public class EnfermedadAdapter extends ArrayAdapter<EnfermedadDTO> {
    public EnfermedadAdapter(Context context, List<EnfermedadDTO> objects) {
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
                    R.layout.list_item_enfermedad,
                    parent,
                    false);
        }

        // Referencias UI.
        TextView id = (TextView) convertView.findViewById(R.id.itm_idEnfermedad);
        TextView nombre = (TextView) convertView.findViewById(R.id.itm_enfermedad);
        TextView gravedad = (TextView) convertView.findViewById(R.id.itm_gravedad);


        EnfermedadDTO enfermedad = getItem(position);

        id.setText("ID: " +enfermedad.getIdEnfermedad().toString());
        nombre.setText("Nombre: " +enfermedad.getNombre());
        gravedad.setText("Gravedad: " +enfermedad.getGradoGravedad().toString());

        return convertView;
    }
}