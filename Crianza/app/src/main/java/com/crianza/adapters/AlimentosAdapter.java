package com.crianza.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.crianza.R;
import com.crianza.dtos.AlimentoDTO;

import java.util.List;

public class AlimentosAdapter extends ArrayAdapter<AlimentoDTO> {
public AlimentosAdapter(Context context, List<AlimentoDTO> objects) {
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
                    R.layout.list_item_alimento,
                    parent,
                    false);
        }

        // Referencias UI.
        TextView id = (TextView) convertView.findViewById(R.id.itm_idAlimento);
        TextView nombre = (TextView) convertView.findViewById(R.id.itm_nombre);
        TextView costoUnidad = (TextView) convertView.findViewById(R.id.itm_costoUnidad);
        TextView cantidad = (TextView) convertView.findViewById(R.id.itm_cantidad);
        TextView unidad = (TextView) convertView.findViewById(R.id.itm_unidad);

        AlimentoDTO alimento = getItem(position);

        id.setText("ID: " +alimento.getIdAlimento().toString());
        nombre.setText("Nombre: " +alimento.getNombre());
        cantidad.setText("Cantidad: " +alimento.getCantidad().toString());
        costoUnidad.setText("Costo Unidad: " +alimento.getCostoUnidad().toString());
        unidad.setText("Unidad: " +alimento.getTipoUnidad());

        return convertView;
    }
}
