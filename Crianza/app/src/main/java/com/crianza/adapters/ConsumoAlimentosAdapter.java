package com.crianza.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.crianza.R;
import com.crianza.dtos.AlimentoDTO;
import com.crianza.dtos.ConsumoAlimentoDTO;

import java.util.List;

public class ConsumoAlimentosAdapter extends ArrayAdapter<ConsumoAlimentoDTO> {
    public ConsumoAlimentosAdapter(Context context, List<ConsumoAlimentoDTO> objects) {
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
                    R.layout.list_item_consumo_alimento,
                    parent,
                    false);
        }

        // Referencias UI.
        TextView idT = (TextView) convertView.findViewById(R.id.itm_idTernero);
        TextView nroCaravana = (TextView) convertView.findViewById(R.id.itm_nroCaravana);
        TextView fecha = (TextView) convertView.findViewById(R.id.itm_fecha);
        TextView nombreAlimento = (TextView) convertView.findViewById(R.id.itm_nombreAlimento);
        TextView cantidad = (TextView) convertView.findViewById(R.id.itm_cantidad);
        TextView unidad = (TextView) convertView.findViewById(R.id.itm_unidad);


        ConsumoAlimentoDTO consumoAlimento = getItem(position);

        idT.setText("ID Ternero: " +consumoAlimento.getIdTernero().toString());
        nroCaravana.setText("Nro Caravana: " +consumoAlimento.getNroCaravana().toString());
        fecha.setText("Fecha: " +consumoAlimento.getFecha());
        nombreAlimento.setText("Alimento: " +consumoAlimento.getAlimento());
        cantidad.setText("Cantidad: " +consumoAlimento.getCantidad().toString());
        unidad.setText("Unidad: " +consumoAlimento.getUnidad());


        return convertView;
    }
}