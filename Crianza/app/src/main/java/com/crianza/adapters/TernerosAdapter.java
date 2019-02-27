package com.crianza.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.crianza.R;
import com.crianza.dtos.AlimentoDTO;
import com.crianza.dtos.TerneroDTO;

import java.util.List;

public class TernerosAdapter  extends ArrayAdapter<TerneroDTO> {
    public TernerosAdapter(Context context, List<TerneroDTO> objects) {
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
                    R.layout.list_item_ternero,
                    parent,
                    false);
        }

        // Referencias UI.
        TextView id = (TextView) convertView.findViewById(R.id.itm_idTernero);
        TextView nroCaravana = (TextView) convertView.findViewById(R.id.itm_nroCaravana);
        TextView guachera = (TextView) convertView.findViewById(R.id.itm_guachera);


        TerneroDTO ternero = getItem(position);

        id.setText("ID: " +ternero.getIdTernero().toString());
        nroCaravana.setText("Nro Caravana: " +ternero.getNroCaravana().toString());
        guachera.setText("Guachera: " +ternero.getIdGuachera().toString());

        return convertView;
    }
}
