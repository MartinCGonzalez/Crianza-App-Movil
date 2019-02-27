package com.crianza.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.crianza.R;
import com.crianza.dtos.EnfermedadDTO;
import com.crianza.dtos.EventoClinicoDTO;

import java.util.List;

public class EventoClinicoAdapter extends ArrayAdapter<EventoClinicoDTO> {
    public EventoClinicoAdapter(Context context, List<EventoClinicoDTO> objects) {
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
                    R.layout.list_item_evento_clinico,
                    parent,
                    false);
        }

        // Referencias UI.
        TextView id = (TextView) convertView.findViewById(R.id.itm_idTernero);
        TextView fechaNac = (TextView) convertView.findViewById(R.id.itm_fechaNac);
        TextView diasVida = (TextView) convertView.findViewById(R.id.itm_diasVida);
        TextView fechaDesde = (TextView) convertView.findViewById(R.id.itm_fechaDesde);
        TextView fechaHasta = (TextView) convertView.findViewById(R.id.itm_fechaHasta);
        TextView enfermedad = (TextView) convertView.findViewById(R.id.itm_enfermedad);
        TextView gravedad = (TextView) convertView.findViewById(R.id.itm_gravedad);

        EventoClinicoDTO eventoClinico = getItem(position);

        id.setText("ID Ternero: " +eventoClinico.getIdTernero().toString());
        fechaNac.setText("Fecha Nacimiento: " +eventoClinico.getFechaNac().toString());
        diasVida.setText("Dias Vida: " +eventoClinico.getDiasVida().toString());
        fechaDesde.setText("Fecha Desde: " +eventoClinico.getFechaDesde().toString());
        fechaHasta.setText("Fecha Hasta: " +eventoClinico.getFechaHasta().toString());
        enfermedad.setText("Enfermedad: " +eventoClinico.getNombreEnfermedad().toString());
        gravedad.setText("Gravedad: " +eventoClinico.getGravedadEnfermedad().toString());

        return convertView;
    }
}