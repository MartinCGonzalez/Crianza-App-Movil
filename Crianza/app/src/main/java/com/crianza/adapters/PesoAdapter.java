package com.crianza.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.crianza.R;
import com.crianza.dtos.AlimentoDTO;
import com.crianza.dtos.PesoDTO;

import java.util.List;

public class PesoAdapter extends ArrayAdapter<PesoDTO> {
    public PesoAdapter(Context context, List<PesoDTO> objects) {
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
                    R.layout.list_item_peso,
                    parent,
                    false);
        }

        // Referencias UI.
        TextView id = (TextView) convertView.findViewById(R.id.itm_idTernero);
        TextView fecha = (TextView) convertView.findViewById(R.id.itm_fecha);
        TextView pesoTer = (TextView) convertView.findViewById(R.id.itm_peso);
        TextView ganancia = (TextView) convertView.findViewById(R.id.itm_gananciaPeso);
        TextView tipoRegistro = (TextView) convertView.findViewById(R.id.itm_tipoRegistro);

        PesoDTO peso = getItem(position);

        id.setText("ID: " +peso.getIdTernero().toString());
        fecha.setText("Fecha: " +peso.getFecha());
        pesoTer.setText("Peso: " +peso.getPeso().toString());
        ganancia.setText("Ganancia: " +peso.getGanancia().toString());
        tipoRegistro.setText("Tipo Registro: " +peso.getTipoRegistro());

        return convertView;

    }
}
